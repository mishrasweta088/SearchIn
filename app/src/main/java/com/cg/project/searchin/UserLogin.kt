package com.cg.project.searchin

import android.R.attr
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception


class UserLogin : AppCompatActivity() {

    lateinit var mEmailEt :EditText
    lateinit var mPasswordEt :EditText
    lateinit var mLoginBtn : Button
    lateinit var mProgressBar : ProgressDialog


    //Declare an instance of FirebaseAuth
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Sign In");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        mEmailEt = findViewById(R.id.emailEt)
        mPasswordEt = findViewById(R.id.passwordEt)
        mLoginBtn = findViewById(R.id.loginBtn)
        mProgressBar = ProgressDialog(this)
        mProgressBar!!.setMessage("Logging User...")

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        findViewById<TextView>(R.id.new_userTV).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@UserLogin, RegisterOption::class.java)
                startActivity(intent)
            }
        })

        //login button click
        mLoginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //input data
                var email : String =mEmailEt.getText().toString()
                var password : String = mPasswordEt.getText().toString()
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email pattern set error
                    mEmailEt.setError("Invalid Email")
                    mEmailEt.setFocusable(true)
                }else{
                    //valid email pattern
                    loginUser(email,password)
                }
            }
        })

    }

    private fun loginUser(email: String, password: String) {
    // show progress dialog
        mProgressBar.show()
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Sign in success, dismiss dialog and start register activity
                    mProgressBar.dismiss()
                    val user: FirebaseUser? = mAuth!!.getCurrentUser()
                    val intent = Intent(this@UserLogin, UserProfile::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    mProgressBar.dismiss()
                    Toast.makeText(this@UserLogin,"Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(OnFailureListener (){
                @Override fun onFailure(e : Exception) {
                    //error, dismiss progress dialog and show the error message
                    mProgressBar.dismiss()
                    Toast.makeText(this@UserLogin, "" + e.getStackTrace(), Toast.LENGTH_SHORT).show()
                }

            })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

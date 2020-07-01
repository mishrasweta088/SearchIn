package com.cg.project.searchin

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
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_organisation_login.*
import java.lang.Exception


class OrganisationLogin : AppCompatActivity() {

    lateinit var oEmailt :EditText
    lateinit var oPasswordt :EditText
    lateinit var oSignBtn : Button
    lateinit var oProgressBar : ProgressDialog


    //Declare an instance of FirebaseAuth
    private var oAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation_login)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Sign In");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        oEmailt= findViewById(R.id.emailt)
        oPasswordt = findViewById(R.id.passwordt)
        oSignBtn  = findViewById(R.id.sign_in)
        oProgressBar = ProgressDialog(this)
        oProgressBar!!.setMessage("Logging User...")

        //In the onCreate() method, initialize the FirebaseAuth instance.
        oAuth = FirebaseAuth.getInstance();

        findViewById<TextView>(R.id.new_orgTV).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@OrganisationLogin, RegisterOption::class.java)
                startActivity(intent)
            }
        })

        //login button click
        sign_in.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //input data
                var email : String =oEmailt.getText().toString()
                var password : String = oPasswordt.getText().toString()
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email pattern set error
                    oEmailt.setError("Invalid Email")
                    oEmailt.setFocusable(true)
                }else{
                    //valid email pattern
                    loginOrganisation(email,password)
                }
            }
        })

    }

    private fun loginOrganisation(email: String, password: String) {
        // show progress dialog
        oProgressBar.show()
        oAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Sign in success, dismiss dialog and start register activity
                    oProgressBar.dismiss()
                    val user: FirebaseUser? = oAuth!!.getCurrentUser()
                    val intent = Intent(this@OrganisationLogin, JobPost::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    oProgressBar.dismiss()
                    Toast.makeText(this@OrganisationLogin,"Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(OnFailureListener (){
                @Override fun onFailure(e : Exception) {
                    //error, dismiss progress dialog and show the error message
                    oProgressBar.dismiss()
                    Toast.makeText(this@OrganisationLogin, "" + e.getStackTrace(), Toast.LENGTH_SHORT).show()
                }

            })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

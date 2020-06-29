package com.cg.project.searchin

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception


class UserLogin : AppCompatActivity() {

    lateinit var mEmailEt :EditText
    lateinit var mPasswordEt :EditText
    lateinit var mLoginBtn : Button
    lateinit var mProgressBar : ProgressDialog
    lateinit var mRecoverPassTv : TextView


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
        mRecoverPassTv = findViewById(R.id.recoverPassTv)
        mLoginBtn = findViewById(R.id.loginBtn)
        mProgressBar = ProgressDialog(this)

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        findViewById<TextView>(R.id.new_userTV).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@UserLogin, RegisterOption::class.java)
                startActivity(intent)
                finish()
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

        //recover pass textView click
        mRecoverPassTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showRecoverPasswordDialog()
            }
        })

    }

    private fun showRecoverPasswordDialog() {
        //alert dialog
        var builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Recover Password")

        // set layer linear layout
        var linearLayout : LinearLayout = LinearLayout(this)

        // views to set in dialog
        val emailEt : EditText = EditText(this)
        emailEt.setHint("Email")
        linearLayout.addView(emailEt)
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

    /*  sets the min width of a EditView to fit a text of n 'M' letters regardless of the actual text
        extension and text size*/
        emailEt.setMinEms(16)

        linearLayout.setPadding(10,10,10,10)
        builder.setView(linearLayout)

        // button recover
        builder.setPositiveButton("Recover", DialogInterface.OnClickListener { _,_ ->

            //Input emAIL
            var email : String = emailEt.getText().toString().trim()
            begainRecovery(email)


        })
       /* builder.setPositiveButton("Recover", DialogInterface.OnClickListener() {
            override fun onClick(dialog: DialogInterface, which : Int ){
                //Input emAIL
                var email : String = emailEt.getText().toString().trim()
                begainRecovery(email)
            }
        })*/



        // button cancel
        builder.setNegativeButton("cancel",DialogInterface.OnClickListener { _, _ ->



        })



      /*  builder.setNegativeButton("cancel", DialogInterface.OnClickListener(){
            override fun onClick(dialog: DialogInterface, which : Int ){
                // dismiss dialog
                dialog.dismiss()
            }
        })*/
         // show dialog
        builder.create().show()
    }

    private fun begainRecovery(email: String){
        // show progress dialog
        mProgressBar!!.setMessage("Sending email...")
        mProgressBar.show()
        mAuth!!.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UserLogin,"Email sent", Toast.LENGTH_SHORT).show()
                    //Sign in success, dismiss dialog and start register activity
                    mProgressBar.dismiss()
//                    val user: FirebaseUser? = mAuth!!.getCurrentUser()
//                    val intent = Intent(this@UserLogin, UserProfile::class.java)
//                    startActivity(intent)
//                    finish()
                } else {
                    // If sign in fails, display a message to the user.
//                    mProgressBar.dismiss()
                    Toast.makeText(this@UserLogin,"Failed...", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(OnFailureListener (){
                @Override fun onFailure(e : Exception) {
                    //get and show proper error message
                    mProgressBar.dismiss()
                    Toast.makeText(this@UserLogin, "" + e.getStackTrace(), Toast.LENGTH_SHORT).show()
                }

            })

    }

    private fun loginUser(email: String, password: String) {
    // show progress dialog
        mProgressBar!!.setMessage("Logging User...")
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

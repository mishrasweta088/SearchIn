
package com.cg.project.searchin

import android.R.attr
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
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
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class UserRegisterrr : AppCompatActivity() {

    lateinit var  mFirstName:EditText
    lateinit var  mLastName:EditText
    lateinit var  mEmail:EditText
    lateinit var  mPassword:EditText
    lateinit var  mCheckbox: CheckBox
    lateinit var  mRegister: Button
    lateinit var mProgressBar : ProgressDialog
    var isValid : Boolean = true

    //Declare an instance of FirebaseAuth
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registerrr)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Create Account");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        mFirstName = findViewById(R.id.firstName)
        mLastName = findViewById(R.id.lastName)
        mEmail = findViewById(R.id.email_id)
        mPassword = findViewById(R.id.password)
        mCheckbox = findViewById(R.id.checkBox)
        mRegister  = findViewById(R.id.agree)
        mProgressBar = ProgressDialog(this)
        mProgressBar!!.setMessage("Registering User...")

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        //handle register button click
        mRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //input details
                val firstName :String =mFirstName.getText().toString().trim()
                val lastName :String =mLastName.getText().toString().trim()
                val email :String =mEmail.getText().toString().trim()
                val password :String =mPassword.getText().toString().trim()

                // validate
                validate()

                //register the user
                registerUser(email,password)
            }
        })
    }


    private fun validate() {
        // Check for a valid name.
        if (mFirstName.getText().toString().isEmpty()) {
            mFirstName.setError(getResources().getString(R.string.name_error))
        }
        if (mLastName.getText().toString().isEmpty()) {
            mLastName.setError(getResources().getString(R.string.name_error))
        }

        // Check for a valid email address.
        if (mEmail.getText().toString().isEmpty()) {
            mEmail.setError(getResources().getString(R.string.email_error));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            mEmail.setError(getResources().getString(R.string.error_invalid_email));
        }

        // Check for a valid password.
        else if (mPassword.getText().toString().isEmpty()) {
            mPassword.setError(getResources().getString(R.string.password_error));
        } else if (mPassword.getText().length < 6) {
            mPassword.setError(getResources().getString(R.string.error_invalid_password));
        }

        //checked checkbox
        if (mCheckbox.isChecked) {
            mCheckbox.setChecked(true)
        }
    }



    private fun registerUser(email: String, password: String) {
        mProgressBar!!.show()
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this, OnCompleteListener <AuthResult>() {
                    @Override
                    fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            //Sign in success, dismiss dialog and start register activity
                            mProgressBar.dismiss()
                            val user: FirebaseUser? = mAuth!!.getCurrentUser()
                            if (user != null) {
                                Toast.makeText(this@UserRegisterrr,"Registered...\n"+user.email, Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@UserRegisterrr, UserProfile::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            mProgressBar.dismiss()
                            Toast.makeText(this@UserRegisterrr,"Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }).addOnFailureListener(OnFailureListener (){
                @Override fun onFailure(e : Exception){
                    //error, dismiss progress dialog and show the error message
                    mProgressBar.dismiss()
                    Toast.makeText(this@UserRegisterrr,""+e.getStackTrace(), Toast.LENGTH_SHORT).show()

                }
            })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}

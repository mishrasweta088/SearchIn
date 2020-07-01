
package com.cg.project.searchin

import android.R.attr
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.HashMap


class RegisterUser : AppCompatActivity() {

    lateinit var  mFirstName:EditText
    lateinit var  mLastName:EditText
    lateinit var  mEmail:EditText
    lateinit var  mPassword:EditText
    lateinit var  mCheckbox: CheckBox
    lateinit var  mRegister: Button
    lateinit var mProgressBar : ProgressDialog
    var isValid : Boolean = true

    //Firebase references
    private var mAuth: FirebaseAuth? = null



    var user = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)




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
//                val firstName :String =mFirstName.getText().toString().trim()
//                val lastName :String =mLastName.getText().toString().trim()
                val email :String =mEmail.getText().toString().trim()
                val password :String =mPassword.getText().toString().trim()

                // validate
                if(validate()) {

                    //register the user
                    registerUser(email, password)
                }
            }
        })
    }


    private fun validate() :Boolean{
        // Check for a valid name.
        var flag:Boolean=true
        if (mFirstName.getText().toString().isEmpty()) {
            mFirstName.setError(getResources().getString(R.string.name_error))
            flag=false
        }
        if (mLastName.getText().toString().isEmpty()) {
            mLastName.setError(getResources().getString(R.string.name_error))
            flag=false
        }

        // Check for a valid email address.
        if (mEmail.getText().toString().isEmpty()) {
            mEmail.setError(getResources().getString(R.string.email_error));
            flag=false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            mEmail.setError(getResources().getString(R.string.error_invalid_email));
            flag=false
        }

        // Check for a valid password.
        else if (mPassword.getText().toString().isEmpty()) {
            mPassword.setError(getResources().getString(R.string.password_error));
            flag=false
        } else if (mPassword.getText().length < 6) {
            mPassword.setError(getResources().getString(R.string.error_invalid_password));
            flag=false
        }

        //checked checkbox
        if (!mCheckbox.isChecked) {
            Toast.makeText(this,"Check the terms and conditions",Toast.LENGTH_LONG).show()
            flag=false
        }
        return flag
    }



    private fun registerUser(email: String, password: String) {
        mProgressBar!!.show()

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Sign in success, dismiss dialog and start register activity
                    mProgressBar.dismiss()
                    val user: FirebaseUser? = mAuth!!.getCurrentUser()
                    if (user != null) {


                        val firstName = mFirstName?.text.toString()
                        val  lastName = mLastName?.text.toString()
                        val email = user!!.email
                        val uid = user!!.uid

                        val hashMap =
                            HashMap<Any, String?>()
                        hashMap["email"] = email
                        hashMap["uid"] = uid
                        hashMap["firstname"]=firstName
                        hashMap["lastname"]=lastName


                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("Userprofile")
                        reference.child(uid).setValue(hashMap)


                        Toast.makeText(this@RegisterUser,"Registered...\n"+user.email, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterUser, UserLogin::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    mProgressBar.dismiss()
                    Toast.makeText(this@RegisterUser,"Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(OnFailureListener (){
                @Override fun onFailure(e : Exception){
                    //error, dismiss progress dialog and show the error message
                    mProgressBar.dismiss()
                    Toast.makeText(this@RegisterUser,""+e.getStackTrace(), Toast.LENGTH_SHORT).show()

                }
            })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}

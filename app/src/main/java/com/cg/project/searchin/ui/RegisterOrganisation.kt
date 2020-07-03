
package com.cg.project.searchin.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.cg.project.searchin.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception
import java.util.HashMap


class RegisterOrganisation : AppCompatActivity() {

    lateinit var  oCompanyName:EditText
    lateinit var  oEmailId:EditText
    lateinit var  oPwd:EditText
    lateinit var  oCheckbox: CheckBox
    lateinit var  oJoin: Button
    lateinit var oProgressBar : ProgressDialog
    var isValid : Boolean = true

    //Firebase references
    private var oAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_organisation)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Create Organisation Account");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        oCompanyName = findViewById(R.id.companyName)
        oEmailId = findViewById(R.id.emailadd)
        oPwd= findViewById(R.id.pass)
        oCheckbox = findViewById(R.id.check_box)
        oJoin  = findViewById(R.id.join)
        oProgressBar = ProgressDialog(this)
        oProgressBar!!.setMessage("Registering User...")

        //In the onCreate() method, initialize the FirebaseAuth instance.
        oAuth = FirebaseAuth.getInstance();


        //handle register button click
        oJoin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //input details
//                val firstName :String =mFirstName.getText().toString().trim()
//                val lastName :String =mLastName.getText().toString().trim()
                val email :String =oEmailId.getText().toString().trim()
                val password :String =oPwd.getText().toString().trim()

                // validate
                if(validate()) {

                    //register the user
                    registerOrganisation(email, password)
                }
            }
        })
    }


    private fun validate() :Boolean{
        // Check for a valid name.
        var flag:Boolean=true
        if (oCompanyName.getText().toString().isEmpty()) {
            oCompanyName.setError(getResources().getString(R.string.name_error))
            flag=false
        }
        // Check for a valid email address.
        if (oEmailId.getText().toString().isEmpty()) {
            oEmailId.setError(getResources().getString(R.string.email_error));
            flag=false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(oEmailId.getText().toString()).matches()) {
            oEmailId.setError(getResources().getString(R.string.error_invalid_email));
            flag=false
        }

        // Check for a valid password.
        else if (oPwd.getText().toString().isEmpty()) {
            oPwd.setError(getResources().getString(R.string.password_error));
            flag=false
        } else if (oPwd.getText().length < 6) {
            oPwd.setError(getResources().getString(R.string.error_invalid_password));
            flag=false
        }

        //checked checkbox
        if (!oCheckbox.isChecked) {
            Toast.makeText(this,"Check the terms and conditions",Toast.LENGTH_LONG).show()
            flag=false
        }
        return flag
    }



    private fun registerOrganisation(email: String, password: String) {
        oProgressBar!!.show()

        oAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Sign in success, dismiss dialog and start register activity
                    oProgressBar.dismiss()
                    val user: FirebaseUser? = oAuth!!.getCurrentUser()
                    if (user != null) {



                        val orgname = oCompanyName?.text.toString()
                        val email = user!!.email
                        val uid = user!!.uid

                        val hashMap =
                            HashMap<Any, String?>()
                        hashMap["email"] = email
                        hashMap["uid"] = uid
                        hashMap["orgname"]=orgname


                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("OrganisationDetails")
                        reference.child(uid).setValue(hashMap)

                        Toast.makeText(this@RegisterOrganisation,"Registered...\n"+user.email, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterOrganisation, UserLogin::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    oProgressBar.dismiss()
                    Toast.makeText(this@RegisterOrganisation,"Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(OnFailureListener (){
                @Override fun onFailure(e : Exception){
                    //error, dismiss progress dialog and show the error message
                    oProgressBar.dismiss()
                    Toast.makeText(this@RegisterOrganisation,""+e.getStackTrace(), Toast.LENGTH_SHORT).show()

                }
            })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}

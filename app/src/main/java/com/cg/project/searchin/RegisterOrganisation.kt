package com.cg.project.searchin

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_organisation.*

class RegisterOrganisation : AppCompatActivity() {

   /* //UI elements
    private var etCompanyName: EditText? = null
    private var etEmail: EditText? = null
    private var btnCreateAccountOrganisation: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountOrganisationActivity"


    //global variables
    private var companyName: String? = null
    private var cemail: String? = null*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_organisation)

        /*etCompanyName = findViewById<View>(R.id.companyName) as EditText
        etEmail = findViewById<View>(R.id.emailadd) as EditText
        btnCreateAccountOrganisation = findViewById<View>(R.id.agree) as Button
        mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccountOrganisation!!.setOnClickListener { createNewAccountOrganisation() }*/
    }

    private fun createNewAccountOrganisation() {

        /*companyName = etCompanyName?.text.toString()

        cemail = etEmail?.text.toString()


        if (!TextUtils.isEmpty(companyName) &&
           !TextUtils.isEmpty(cemail)) {


        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }

        mProgressBar!!.setMessage("Registering User...")
        mProgressBar!!.show()

        mAuth!!
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                mProgressBar!!.hide()

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val userId = mAuth!!.currentUser!!.uid

                    //Verify Email
                    verifyEmail();

                    //update user profile information
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)

                    updateUserInfoAndUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this@RegisterUser, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }*/
    }

}

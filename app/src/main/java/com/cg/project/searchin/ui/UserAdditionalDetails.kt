package com.cg.project.searchin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cg.project.searchin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.HashMap

class UserAdditionalDetails : AppCompatActivity() {

    lateinit var  mState: EditText
    lateinit var  mDesignation: EditText
    lateinit var  mSkills: EditText
    lateinit var  mEducation: EditText
    lateinit var savebtn_user: Button

    private var mAuth: FirebaseAuth? = null

//    lateinit var firebaseAuth : FirebaseAuth
    lateinit var user : FirebaseUser
    lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_additional_details)


        mState = findViewById(R.id.state)
        mDesignation = findViewById(R.id.designation)
        mSkills = findViewById(R.id.skills)
        mEducation = findViewById(R.id.education)
        savebtn_user=findViewById(R.id.savebtn_user)

        mAuth = FirebaseAuth.getInstance();



        savebtn_user.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //input details
//                val firstName :String =mFirstName.getText().toString().trim()
//                val lastName :String =mLastName.getText().toString().trim()

                // validate
                if(validate()) {

                    //add job details
                    userAdditionalDetails()
                }
            }
        })


        //View user Additional Details

//
//
//        val user: FirebaseUser? = mAuth!!.getCurrentUser()
//        var query : Query = firebaseDatabase.getReference("UserAdditionalDetails/"+user?.uid)
//        query.addListenerForSingleValueEvent( object : ValueEventListener {
//            override fun onDataChange(
//                ds: DataSnapshot
//            ) {
//                if (user != null) {
//
//                try {
//                    //get data
//                    val state = "" +ds.child("state").getValue().toString()
//                    val designation = "" + ds.child("designation").getValue().toString()
//                    val skills=""+ds.child("skills").getValue().toString()
//                    val education=""+ds.child("education").getValue().toString()
//
//
//                    //set data
//                    mState.setText(state)
//                    mDesignation.setText(designation)
//                    mSkills.setText(skills)
//                    mEducation.setText(education)
//
//
//                } catch (e: NullPointerException) {
//
//                    e.printStackTrace()
//                }
//
//            }
//            }
//
//
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })


    }


    private fun validate() :Boolean{
        // Check for a valid oj job details.
        var flag:Boolean=true
        if (mState.getText().toString().isEmpty()) {
            mState.setError(getResources().getString(R.string.name_error))
            flag=false
        }
        if (mDesignation.getText().toString().isEmpty()) {
            mDesignation.setError(getResources().getString(R.string.name_error))
            flag=false
        }

        if (mSkills.getText().toString().isEmpty()) {
            mSkills.setError(getResources().getString(R.string.name_error));
            flag=false
        }
        if (mEducation.getText().toString().isEmpty()) {
            mEducation.setError(getResources().getString(R.string.name_error));
            flag=false
        }
        return flag
    }


    private fun userAdditionalDetails() {
        // mProgressBar!!.show()
        val user: FirebaseUser? = mAuth!!.getCurrentUser()
        if (user != null) {
            val state = mState?.text.toString()
            val  designation = mDesignation?.text.toString()
            val skills = mSkills?.text.toString()
            val education = mEducation?.text.toString()
            val email = user!!.email
            val uid = user!!.uid

            val hashMap =
                HashMap<Any, String?>()
            hashMap["state"] = state
            hashMap["designation"] = designation
            hashMap["skills"]=skills
            hashMap["email"]=email
            hashMap["education"]=education

            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("UserAdditionalDetails")
            reference.child(uid).setValue(hashMap)

            Toast.makeText(this@UserAdditionalDetails,"Successfully Posted", Toast.LENGTH_SHORT).show()

        }


    }

}

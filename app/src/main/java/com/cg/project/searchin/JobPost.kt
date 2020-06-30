package com.cg.project.searchin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.HashMap

class JobPost : AppCompatActivity() {

    lateinit var  mOrgname: EditText
    lateinit var  mJobid: EditText
    lateinit var  mDescription: EditText
    lateinit var  mRequirements: EditText
    lateinit var savebtn:Button
    lateinit var reff: DatabaseReference

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_post)


        mOrgname = findViewById(R.id.orgname)
        mJobid = findViewById(R.id.jobid)
        mDescription = findViewById(R.id.description)
        mRequirements = findViewById(R.id.requirements)
        savebtn=findViewById(R.id.savebtn)

        mAuth = FirebaseAuth.getInstance();

        savebtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //input details
//                val firstName :String =mFirstName.getText().toString().trim()
//                val lastName :String =mLastName.getText().toString().trim()

                // validate
                if(validate()) {

                    //add job details
                    jobdetails()
                }
            }
        })

    }


    private fun validate() :Boolean{
        // Check for a valid oj job details.
        var flag:Boolean=true
        if (mOrgname.getText().toString().isEmpty()) {
            mOrgname.setError(getResources().getString(R.string.name_error))
            flag=false
        }
        if (mJobid.getText().toString().isEmpty()) {
            mJobid.setError(getResources().getString(R.string.name_error))
            flag=false
        }

        if (mDescription.getText().toString().isEmpty()) {
            mDescription.setError(getResources().getString(R.string.name_error));
            flag=false
        }
        if (mRequirements.getText().toString().isEmpty()) {
            mRequirements.setError(getResources().getString(R.string.name_error));
            flag=false
        }
        return flag
    }


    private fun jobdetails() {
       // mProgressBar!!.show()
        val user: FirebaseUser? = mAuth!!.getCurrentUser()
        if (user != null) {
        val OrgName = mOrgname?.text.toString()
        val  JobId = mJobid?.text.toString()
        val JobDescription = mDescription?.text.toString()
        val JobRequirements = mRequirements?.text.toString()
            val email = user!!.email
            val uid = user!!.uid

        val hashMap =
            HashMap<Any, String?>()
        hashMap["organisationName"] = OrgName
        hashMap["jobId"] = JobId
            hashMap["email"]=email
        hashMap["jobDescription"]=JobDescription
        hashMap["jobRequiremts"]=JobRequirements

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("JobDetails")
        reference.child(uid).setValue(hashMap)

            Toast.makeText(this@JobPost,"Successfully Posted", Toast.LENGTH_SHORT).show()

        }


    }

    }

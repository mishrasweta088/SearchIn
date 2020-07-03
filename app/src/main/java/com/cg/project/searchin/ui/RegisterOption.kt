package com.cg.project.searchin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.cg.project.searchin.R

class RegisterOption : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_option)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Create Account");
        
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        var mRegisterOrganization: Button = findViewById(R.id.registerOrganisation_btn)
        var mRegisterUser: Button = findViewById(R.id.registerUser_btn)

        mRegisterOrganization.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@RegisterOption, RegisterOrganisation::class.java)
                startActivity(intent)
            }
        })

        mRegisterUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@RegisterOption, RegisterUser::class.java)
                startActivity(intent)
            }
        })

        findViewById<TextView>(R.id.have_accountTV).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@RegisterOption, UserLogin::class.java)
                startActivity(intent)
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
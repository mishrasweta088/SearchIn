package com.cg.project.searchin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBar

class RegisterOption : AppCompatActivity() {

    internal lateinit var mRegisterOrganization: Button
    internal lateinit var mRegisterUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_option)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Create Account");
        
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true)

        mRegisterOrganization = findViewById(R.id.registerOrganisation_btn)
        mRegisterUser = findViewById(R.id.registerUser_btn)

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
    }
}
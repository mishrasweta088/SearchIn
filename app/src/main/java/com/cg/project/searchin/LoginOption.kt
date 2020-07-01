package com.cg.project.searchin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginOption : AppCompatActivity() {
    lateinit var  mLoginUser: Button
    lateinit var  mLoginOrganisation: Button
    lateinit var mRegister:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_option)
         mLoginUser = findViewById(R.id.loginUser_btn)
        mLoginOrganisation= findViewById(R.id.loginOrganisation_btn)
        mRegister=findViewById(R.id.new_userTV)

        mLoginUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@LoginOption, UserLogin::class.java)
                startActivity(intent)
            }
        })

        mLoginOrganisation.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@LoginOption, OrganisationLogin::class.java)
                startActivity(intent)
            }
        })



        mRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@LoginOption, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
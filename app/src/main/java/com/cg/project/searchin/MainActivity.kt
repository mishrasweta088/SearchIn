package com.cg.project.searchin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init
       var  mRegisterBtn : Button = findViewById(R.id.register_btn)
       var  mLoginBtn : Button = findViewById(R.id.login_btn)

        // handle register button click
        mRegisterBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@MainActivity, RegisterOption::class.java)
                startActivity(intent)
            }
        })

        mLoginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start Registration Option
                val intent = Intent(this@MainActivity, UserLogin::class.java)
                startActivity(intent)
            }
        })
    }
}

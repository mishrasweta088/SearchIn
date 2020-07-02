package com.cg.project.searchin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    //views from xml
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var profileIv : ImageView
    lateinit var nameTv : TextView
    lateinit var userStatusTv : TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //init views
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        toolbar.setTitle("")

        recyclerView = findViewById(R.id.chat_recyclerView)
        profileIv = findViewById(R.id.profileTV)
        nameTv = findViewById(R.id.nameTv)
        userStatusTv= findViewById(R.id.userStatusTv)
        messageEt = findViewById(R.id.messageEt)
        sendBtn = findViewById(R.id.sendBtn)


    }
}

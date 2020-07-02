package com.cg.project.searchin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    //views from xml
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var profileIv : ImageView
    lateinit var nameTv : TextView
    lateinit var userStatusTv : TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn : Button

    //firebase auth
    lateinit var firebaseAuth: FirebaseAuth

    var firebaseDatabase: FirebaseDatabase? =null
    var userDbRef :DatabaseReference? = null

    var hisUid:String? = null
    var myUid:String? = null

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


        /*on clicking user from user list we have passed that user's uid using intentso get that
          uid here to get  the profile picture , name, and start chat with that user*/
        var intent=getIntent()
        hisUid = intent.getStringExtra("hisUid")

        //init
        firebaseAuth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        userDbRef = firebaseDatabase!!.getReference("UserProfile")

        // search user to get that users info
        var userQuery :Query = userDbRef!!.orderByChild("uid").equalTo(hisUid)

        //get user picture and name
        userQuery.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.getChildren()){
                    // get data
                    var name :String = ""+dataSnapshot.child("name").getValue()
                    var image : String =""+dataSnapshot.child("image").getValue()

                    //set Data
                    nameTv.setText(name)
                    try {
                        //image received , set it to imageview
                        Picasso.get().load(image).placeholder(R.drawable.ic_default_image_white).into(profileIv)

                    }catch (e : Exception){
                        Picasso.get().load(R.drawable.ic_default_image_white).into(profileIv)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        sendBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //get text from edit text
                var message: String = messageEt.getText().toString().trim()
                // check if text is empty or not
                if(TextUtils.isEmpty(message)){
                    //text empty
                    Toast.makeText(this@ChatActivity,"Cannot send the empty message...",Toast.LENGTH_SHORT).show()
                }else{
                    //text not empty
                    sendMessage(message)
                }
            }
        })
    }

    private fun sendMessage(message: String) {
        var databaseReference:DatabaseReference = FirebaseDatabase.getInstance().getReference()

        var hashMap : HashMap<String, String> = HashMap<String, String>()
        myUid?.let { hashMap.put("sender", it) }
        hisUid?.let { hashMap.put("receiver", it) }
        hashMap.put("message",message)
        databaseReference.child("Chats").push().setValue(hashMap)

        //reset edittext after sending message
        messageEt.setText("")

    }

    private fun checkUserStatus(){
        //get current user
        var user: FirebaseUser? = firebaseAuth.getCurrentUser()
        if(user != null){
            // user is signed in stay here
            //set email of logged user
            // mProfileTv.setText(user.getEmail())
            myUid=user.getUid() //currently signed user's uid

        }else{
            //user not signed in, go to main activity
            val intent = Intent(this@ChatActivity, MainActivity::class.java)
            finish()
        }
    }

    override fun onStart() {
        checkUserStatus()
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //inflate menu
        menuInflater.inflate(R.menu.menu_main, menu)

        //hide searchview , as we dont need it here
        menu.findItem(R.id.action_search).setVisible(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // get option id
        var id = item.getItemId()
        if(id == R.id.action_logout){
            firebaseAuth.signOut()
            checkUserStatus()
        }

        return super.onOptionsItemSelected(item)
    }
}

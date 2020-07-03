package com.cg.project.searchin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    //views from xml
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var profileIv : ImageView
    lateinit var nameTv : TextView
    lateinit var userStatusTv : TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn : ImageButton

    //firebase auth
    lateinit var firebaseAuth: FirebaseAuth

    var firebaseDatabase: FirebaseDatabase? =null
    var userDbRef :DatabaseReference? = null

    //for checking if user has seen message or not
    lateinit var  seenListener: ValueEventListener
    lateinit var userRefForSeen : DatabaseReference


    lateinit var chatList : List<ModelChat>
    lateinit var adapterChat: AdapterChat

    var hisUid:String? = null
    var myUid:String? = null
    var hisImage:String? = null



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

        //Layout (LinearLayout) for RecyclerView
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.setStackFromEnd(true)

        //recyclerview properties
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(linearLayoutManager)

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
                    hisImage  =""+dataSnapshot.child("image").getValue()

                    //set Data
                    nameTv.setText(name)
                    try {
                        //image received , set it to imageview
                        Picasso.get().load(hisImage).placeholder(R.drawable.ic_default_image_white).into(profileIv)

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

        readMessages()
        seenMessage()
    }

    private fun seenMessage() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats")
        seenListener = userRefForSeen.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.getChildren()) {
                    var chat: ModelChat? = ds.getValue(ModelChat::class.java)
                    if (chat!!.receiver.equals(myUid) && chat.sender.equals(hisUid)) {
                        var hasSeenHashMap: HashMap<String, Any> = HashMap()
                        hasSeenHashMap.put("isSeen", true)
                        ds.getRef().updateChildren(hasSeenHashMap)

                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun readMessages() {
        chatList=ArrayList<ModelChat>()
        var dbRef:DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")
        dbRef.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chatList.clear()
                for (ds in dataSnapshot.getChildren()){
                    var chat: ModelChat? = ds.getValue(ModelChat::class.java)
                    if(chat!!.receiver.equals(myUid) && chat.sender.equals(hisUid) ||
                        chat!!.receiver.equals(hisUid) && chat.sender.equals(myUid)){
                        (chatList as ArrayList<ModelChat>).add(chat)
                    }

                   /* //adaptor
                    adapterChat = AdapterChat(this@ChatActivity,
                        chatList as ArrayList<ModelChat>,hisImage)
                    adapterChat.notifyDataSetChanged()
                    //set adaptor to recyclerview
                    recyclerView.setAdapter(adapterChat)*/
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })

    }

    private fun sendMessage(message: String) {
        var databaseReference:DatabaseReference = FirebaseDatabase.getInstance().getReference()

        var timestamp :String= System.currentTimeMillis().toString()

        var hashMap : HashMap<String, Any> = HashMap<String, Any>()
        myUid?.let { hashMap.put("sender", it) }
        hisUid?.let { hashMap.put("receiver", it) }
        hashMap.put("message",message)
        hashMap.put("timestamp",timestamp)
        hashMap.put("isSeen",false)
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


    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        checkUserStatus()
        super.onStart()
        userRefForSeen.removeEventListener(seenListener)
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

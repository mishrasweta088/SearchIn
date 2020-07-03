package com.cg.project.searchin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Home : AppCompatActivity() {

    //firebase auth
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var navigationView: BottomNavigationView

    //views


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Actionbar and its title
        var actionBar: ActionBar? = getSupportActionBar();
        actionBar!!.setTitle("Dashboard");

        //init
        firebaseAuth = FirebaseAuth.getInstance()
        // mProfileTv = findViewById(R.id.profileTv)

        navigationView = findViewById<BottomNavigationView>(R.id.navigation)
        navigationView.setOnNavigationItemSelectedListener(selectedListener)

        // home fragment transacion(default)
       var fragment1:HomeFragment = HomeFragment()
        var ft1:FragmentTransaction=supportFragmentManager.beginTransaction()
        ft1.replace(R.id.content, fragment1, "")
        ft1.commit()

       // onBackPressed()

    }

    /*  private val selectedListener= BottomNavigationView.OnNavigationItemSelectedListener() {

          fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
              when (menuItem.itemId) {
                  R.id.nav_home -> return@OnNavigationItemSelectedListener true
                  R.id.nav_profile -> return@OnNavigationItemSelectedListener true
                  R.id.nav_myConnect -> return@OnNavigationItemSelectedListener true

              }
              return false

          }*/
    private val selectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // home fragment transacion
                    actionBar?.setTitle("Hello")
                   var fragment1:HomeFragment = HomeFragment()
                    var ft1:FragmentTransaction=supportFragmentManager.beginTransaction()
                    ft1.replace(R.id.content, fragment1, "")
                    ft1.commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    actionBar?.setTitle("Profile")
                    var fragment2:ProfileFragment = ProfileFragment()
                    var ft2:FragmentTransaction=supportFragmentManager.beginTransaction()
                    ft2.replace(R.id.content, fragment2, "")
                    ft2.commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_myConnect -> {
                    actionBar?.setTitle("My Connect")
                    var fragment3:MyConnectFragment = MyConnectFragment()
                    var ft3:FragmentTransaction=supportFragmentManager.beginTransaction()
                    ft3.replace(R.id.content, fragment3, "")
                    ft3.commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_chat -> {
                    actionBar?.setTitle("Chats")
                    var fragment4: ChatListFragment = ChatListFragment()
                    var ft4: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft4.replace(R.id.content, fragment4, "")
                    ft4.commit()

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    private fun checkUserStatus(){
        //get current user
        var user: FirebaseUser? = firebaseAuth.getCurrentUser()
        if(user != null){
            // user is signed in stay here
            //set email of logged user
            // mProfileTv.setText(user.getEmail())
        }else{
            //user not signed in, go to main activity
            val intent = Intent(this@Home, MainActivity::class.java)
            finish()
        }
    }

    override fun onStart() {
        //check on start of app
        checkUserStatus()
        super.onStart()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // get option id
        var id = item.getItemId()
        if(id == R.id.action_logout){
            firebaseAuth.signOut()
            checkUserStatus()
        }

      if(id==R.id.action_add_post){
            var intent:Intent=Intent(this@Home,AddPostActivity::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }



   override fun onBackPressed() {

       val builder= AlertDialog.Builder(this)
       builder.setTitle("Are you sure")
       builder.setMessage("Do you want to quit?")
       builder.setPositiveButton("Yes",{ dialogInterface: DialogInterface?, i: Int ->finish()  })
       builder.setNegativeButton("No",{ dialogInterface: DialogInterface?, i: Int -> })
       val dialog: AlertDialog =builder.create()
       dialog.show()
       // builder.show()

       true
    }
}

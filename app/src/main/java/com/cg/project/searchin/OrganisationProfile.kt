package com.cg.project.searchin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class OrganisationProfile : AppCompatActivity() {

    //firebase auth
    lateinit var firebaseAuth : FirebaseAuth

    //views
    lateinit var oProfileOrg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation_profile)

        //Actionbar and its title
        var  actionBar: ActionBar? =  getSupportActionBar();
        actionBar!!.setTitle("Organisation Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance()
        oProfileOrg = findViewById(R.id.profileOrg)

    }

    private fun checkUserStatus(){
        //get current organisation
        var organisation: FirebaseUser? = firebaseAuth.getCurrentUser()
        if(organisation != null){
            // user is signed in stay here
            //set email of logged user
            oProfileOrg.setText(organisation.getEmail())
        }else{
            //user not signed in, go to main activity
            val intent = Intent(this@OrganisationProfile, MainActivity::class.java)
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
        return super.onOptionsItemSelected(item)
    }
}

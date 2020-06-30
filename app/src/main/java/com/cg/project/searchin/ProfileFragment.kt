package com.cg.project.searchin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


//import com.project.searchin.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    //firebase
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var user : FirebaseUser
    lateinit var firebaseDatabase: FirebaseDatabase

    //views
    lateinit var avatarTv : ImageView
    lateinit var nameTv : TextView
    lateinit var emailTv : TextView
    lateinit var phoneTv : TextView


    //views from xml



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View =inflater.inflate(R.layout.fragment_profile, container, false)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.getCurrentUser()!!
        firebaseDatabase = FirebaseDatabase.getInstance()

        //init views
        avatarTv = view.findViewById(R.id.avatarTV)
        nameTv = view.findViewById(R.id.nameTv)
        emailTv = view.findViewById(R.id.emailTv)
        phoneTv = view.findViewById(R.id.phoneTv)

        var query : Query = firebaseDatabase.getReference("Userprofile/"+user.uid)
        query.addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onDataChange(
                ds: DataSnapshot) {

                try {
                    //get data
                    val name = "" +ds.child("firstname").getValue().toString()
                    val email = "" + ds.child("email").getValue().toString()
                    val phone = "" + ds.child("phone").getValue().toString()
                    val image = "" + ds.child("image").getValue().toString()

                    //set data
                    nameTv.setText(name)
                    emailTv.setText(email)
                    phoneTv.setText(phone)
                    try {
                        //if image is received then set
                        Picasso.get().load(image).into(avatarTv)
                    } catch (e: Exception) {
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarTv)
                    }
                } catch (e: NullPointerException) {

                    e.printStackTrace()
                }

            }


            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


      //  val view:View =inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

}

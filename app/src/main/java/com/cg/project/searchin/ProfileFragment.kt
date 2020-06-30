package com.cg.project.searchin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.lang.Exception

//import com.project.searchin.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    //firebase
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var user : FirebaseUser
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    //views
    lateinit var avatarTv : ImageView
    lateinit var nameTv : TextView
    lateinit var emailTv : TextView
    lateinit var phoneTv : TextView

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
        databaseReference = firebaseDatabase.getReference("UserProfile")

        //init views
        avatarTv = view.findViewById(R.id.avatarTV)
        nameTv = view.findViewById(R.id.nameTv)
        emailTv = view.findViewById(R.id.emailTv)
        phoneTv = view.findViewById(R.id.phoneTv)

        var query : Query = databaseReference.orderByChild("email").equalTo(user.getEmail())
        query.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //check until required data get
                for (ds in dataSnapshot.getChildren()) {

                    //get data
                    val name = "" + ds.child("name").getValue()
                    val email = "" + ds.child("email").getValue()
                    val phone = "" + ds.child("phone").getValue()
                    val image = "" + ds.child("image").getValue()


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
                }
            }


            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        return view
    }

}

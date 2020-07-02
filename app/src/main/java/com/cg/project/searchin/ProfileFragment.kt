package com.cg.project.searchin

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    lateinit var mlastname:TextView
    lateinit var emailTv : TextView
    lateinit var madditional:TextView

    lateinit var mstate:TextView
    lateinit var mdesignation:TextView
    lateinit var mskills:TextView
    lateinit var meducation:TextView


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
        mlastname=view.findViewById(R.id.lastname)
        emailTv = view.findViewById(R.id.emailTv)
        madditional=view.findViewById(R.id.additional)
        mstate=view.findViewById(R.id.state)
        mdesignation=view.findViewById(R.id.designation)
        mskills=view.findViewById(R.id.skills)
        meducation=view.findViewById(R.id.education)

        madditional.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
             val intent:Intent= Intent(getActivity(),UserAdditionalDetails::class.java)
                startActivity(intent)

                }
            })




        //view additional data
        var queryuser : Query = firebaseDatabase.getReference("UserAdditionalDetails/"+user.uid)
        queryuser.addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onDataChange(
                ds: DataSnapshot) {

                try {
                    //get data

                    val image = "" + ds.child("image").getValue().toString()
                    val state=""+ds.child("state").getValue().toString()
                    val designation=""+ds.child("designation").getValue().toString()
                    val skills=""+ds.child("skills").getValue().toString()
                    val education=""+ds.child("education").getValue().toString()

                    //set data
                    mstate.setText(state)
                    mdesignation.setText(designation)
                    mskills.setText(skills)
                    meducation.setText(education)


                } catch (e: NullPointerException) {

                    e.printStackTrace()
                }

            }


            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })





        var query : Query = firebaseDatabase.getReference("Userprofile/"+user.uid)
        query.addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onDataChange(
                ds: DataSnapshot) {

                try {
                    //get data
                    val name = "" +ds.child("firstname").getValue().toString()
                    val lastname="" +ds.child("lastname").getValue().toString()
                    val email = "" + ds.child("email").getValue().toString()
//                    val phone = "" + ds.child("phone").getValue().toString()
                    val image = "" + ds.child("image").getValue().toString()
                    val state=""+ds.child("state").getValue().toString()
                    val designation=""+ds.child("designation").getValue().toString()
                    val skills=""+ds.child("skills").getValue().toString()
                    val education=""+ds.child("education").getValue().toString()

                    //set data
                    nameTv.setText(name)
                    mlastname.setText(lastname)
                    emailTv.setText(email)

//                    try {
//                        //if image is received then set
//                        Picasso.get().load(image).into(avatarTv)
//                    } catch (e: Exception) {
//                        //if there is any exception while getting image then set default
//                        Picasso.get().load(R.drawable.img_165522).into(avatarTv)
//                    }
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

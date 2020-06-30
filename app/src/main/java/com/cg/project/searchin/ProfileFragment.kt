package com.cg.project.searchin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

//import com.project.searchin.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

//firebase
  lateinit var firebaseAuth:FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var user:Firebase

    //views from xml



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view:View =inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

}

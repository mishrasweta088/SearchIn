package com.cg.project.searchin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


/**
 * A simple [Fragment] subclass.
 */
class MyConnectFragment : Fragment() {

    var recycleView: RecyclerView? = null
    var adapterUsers: AdapterUsers? = null
    var userList: MutableList<ModelUsers>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =inflater.inflate(R.layout.fragment_my_connect, container, false)

        //init recyclerview
        recycleView = view.findViewById(R.id.connect_recyclerView)
        //set it's properties
        recycleView?.setHasFixedSize(true)
        recycleView?.setLayoutManager(LinearLayoutManager(activity))
        //init user list
        userList = ArrayList<ModelUsers>()
        //getAll Users
        getAllUsers()
        return view


    }

    private fun getAllUsers() {
        //get current user
        val fUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        //get path of database named "Users" containing users info
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Userprofile")

        //get all data from path
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()

                for (ds in dataSnapshot.children) {
                   val modelUser: ModelUsers? = ds.getValue(ModelUsers::class.java)
                    //get all users except currently signed in user

                    userList!!.add(modelUser!!)

                    Log.i("test", (ds.getValue(ModelUsers::class.java))!!.email)

                }

                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
                recycleView!!.setLayoutManager(layoutManager)
                adapterUsers = AdapterUsers(activity,userList!!)
                recycleView!!.setAdapter(adapterUsers)
                //adapter

                //adapterUsers = AdapterUsers(getActivity(), userList)
                //set adapter of recycler view


            }
        })
    }


}

fun <E> List<E>?.add(modelUsers: ModelUsers?) {

}


fun ModelUsers?.getUid() {

}

fun <E> List<E>?.clear() {

}



package com.cg.project.searchin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//import com.project.searchin.R

/**
 * A simple [Fragment] subclass.
 */
class MyConnectFragment : Fragment() {

    var recycleView: RecyclerView? = null
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
        return view


    }

}

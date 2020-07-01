package com.cg.project.searchin


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cg.project.searchin.AdapterUsers.MyHolder
import com.squareup.picasso.Picasso


class AdapterUsers : RecyclerView.Adapter<AdapterUsers.MyHolder> {
    var context: Context? = null
    var usersList: MutableList<ModelUsers>? = null

    constructor(fragmentActivity: FragmentActivity?, userList: MutableList<ModelUsers>?)
    {
        context=fragmentActivity
        this.usersList=userList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return usersList!!.size
    }


    inner class MyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mAvatarTv: ImageView
        var mNameTv: TextView
        var mEmailTv: TextView

        init {
            mAvatarTv = itemView.findViewById(R.id.avatarTV)
            mNameTv = itemView.findViewById(R.id.nameTv)
            mEmailTv = itemView.findViewById(R.id.emailTv)
        }
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        //get data
        val userImage = usersList!![i].image
        val userName = usersList!![i].name
        val userEmail = usersList!![i].email

        //set data
        myHolder.mNameTv.text = userName
        myHolder.mEmailTv.text = userEmail
        try {
            Picasso.get().load(userImage).placeholder(R.drawable.ic_default_img)
                .into(myHolder.mAvatarTv)
        } catch (e: Exception) {
        }
        //handle item click
        myHolder.itemView.setOnClickListener {
            Toast.makeText(
                context,
                "" + userEmail,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
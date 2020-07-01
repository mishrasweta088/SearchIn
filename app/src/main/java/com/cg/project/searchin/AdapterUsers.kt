package com.cg.project.searchin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cg.project.searchin.AdapterUsers.MyHolder
import com.squareup.picasso.Picasso

class AdapterUsers : RecyclerView.Adapter<MyHolder> {
    var context: Context? = null
    var usersList: List<ModelUsers>? = null

    constructor(context: Context?) {
        this.context = context
    }

    constructor(usersList: List<ModelUsers>?) {
        this.usersList = usersList
    }

    constructor(fragmentActivity: FragmentActivity?, userList: List<ModelUsers>?)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {

        //inflate layout(row_user.xml)
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup)
        return MyHolder(view)
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
}
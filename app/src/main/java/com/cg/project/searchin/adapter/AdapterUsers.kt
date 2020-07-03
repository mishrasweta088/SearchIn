package com.cg.project.searchin.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cg.project.searchin.service.ChatActivity
import com.cg.project.searchin.model.ModelUsers
import com.cg.project.searchin.R
import com.cg.project.searchin.fragment.getUid
import com.squareup.picasso.Picasso


class AdapterUsers : RecyclerView.Adapter<AdapterUsers.MyHolder> {

    var context: Context? = null
    var usersList: MutableList<ModelUsers>? = null


    constructor(context: Context?, userList: MutableList<ModelUsers>)
    {
        this.context=context
        this.usersList=userList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyHolder {
        //inflate layout(row_user xml)
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        //get data
        val hisUID = usersList!!.get(i).getUid()
        val userImage = usersList!![i].image
        val userName = usersList!![i].firstname
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
        myHolder.itemView.setOnClickListener {v ->
            /*click user from user list to start chatting/messaging
            start activity by putting uid of receiver
            we will use that uid to identify the user we are gonna chat  */
            var intent:Intent = Intent(context,
                ChatActivity::class.java)
            intent.putExtra("hisUid",hisUID.toString())
            context!!.startActivity(intent)

        }
    }



    override fun getItemCount(): Int {
        return usersList!!.size
    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
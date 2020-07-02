package com.cg.project.searchin


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class AdapterUsers : RecyclerView.Adapter<AdapterUsers.MyHolder> {

    var context: Context? = null
    var usersList: List<ModelUsers>? = null


    constructor(context: Context?, userList: List<ModelUsers >)
    {
        this.context=context
        this.usersList=userList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyHolder {
        //inflate layout(row_user xml)
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        //get data
        val hisUID = usersList!!.get(i).getUid()
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
        myHolder.itemView.setOnClickListener {v ->
            /*click user from user list to start chatting/messaging
            start activity by putting uid of receiver
            we will use that uid to identify the user we are gonna chat  */
            var intent:Intent = Intent(context,ChatActivity::class.java)
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
package com.cg.project.searchin

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*


class AdapterChat : RecyclerView.Adapter<AdapterUsers.MyHolder>{

    private val MSG_TYPE_LEFT:Int =0
    private val MSG_TYPE_RIGHT:Int =1
    lateinit var context :Context
    lateinit var chatList : List<ModelChat>
    lateinit var imageUrl:String
     //firebase
    lateinit var fUser:FirebaseUser

    constructor() : super()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyHolder {
        //inflate Layouts: row chat left.xml for receiver,row chat right .xml for sender
        if (viewType==MSG_TYPE_RIGHT){
            val view =
                LayoutInflater.from(context).inflate(R.layout.row_chat_right, viewGroup)
            return MyHolder(view)
        }else{
            var view:View= LayoutInflater.from(context).inflate(R.layout.row_chat_left,viewGroup,false)
            return MyHolder(view)
        }
    }
    override fun onBindViewHolder(holder: AdapterUsers.MyHolder, position: Int) {
        //get data
        var message : String? = chatList.get(position).message
        var timeStamp : String? = chatList.get(position).timestamp

        //convert time stamp to dd/mm/yyyy hh:mm
        var cal :Calendar= Calendar.getInstance(Locale.ENGLISH)
        cal.setTimeInMillis((timeStamp)!!.toLong())
        var dateTime : String = DateFormat.format("dd/mm/yyyy hh:mm: aa",cal).toString()

        //set data
        holder.messageTV
    }

    override fun getItemCount(): Int {
        return chatList.size
         }

    override fun getItemViewType(position: Int): Int {
        //get currently sign in user
        fUser= FirebaseAuth.getInstance().getCurrentUser()!!
        if (chatList.get(position).sender.equals(fUser.getUid())){
            return MSG_TYPE_RIGHT
        }else{
            return MSG_TYPE_LEFT
        }

    }




    //view holder class
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var profileTv: ImageView
        lateinit var messageTv: TextView
        lateinit var timeTv: TextView
        lateinit var isSeenTv :TextView

        //init
        init {
            profileTv = itemView.findViewById(R.id.profileTV)
            messageTv = itemView.findViewById(R.id.messageTv)
            timeTv = itemView.findViewById(R.id.timeTv)
            isSeenTv = itemView.findViewById(R.id.isSeenTv)
        }
    }


}
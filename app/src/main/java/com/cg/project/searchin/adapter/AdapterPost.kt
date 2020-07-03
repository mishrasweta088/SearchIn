package com.cg.project.searchin.adapter


import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.cg.project.searchin.model.ModelPost
import com.cg.project.searchin.R
import com.squareup.picasso.Picasso
import java.util.*

class AdapterPosts(
    var context: android.content.Context,
    var postList: List<ModelPost>
) : RecyclerView.Adapter<AdapterPosts.MyHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): MyHolder {

        //inflate layout row_post.xml
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_post, viewGroup, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(
        myHolder: MyHolder,
        i: Int
    ) {
        //get data
        val uId = postList[i].uid
        val uEmail = postList[i].uEmail
        val uName = postList[i].uName
        val uDp = postList[i].uDp
        val pId = postList[i].pId
        val pTitle = postList[i].pTitle
        val pDescription = postList[i].pDescr
        val pImage = postList[i].pImage
        val pTimeStamp = postList[i].pTime

        //convert timestamp to dd/mm/yyyy  hh:mm  am/pm
        val calender =
            Calendar.getInstance(Locale.getDefault())
        calender.timeInMillis = pTimeStamp!!.toLong()
        val pTime =
            DateFormat.format("dd/MM/yyyy hh:mm aa", calender).toString()

        //set data
        myHolder.uNameTv.text = uName
        myHolder.pTimeTv.text = pTime
        myHolder.pTitltTv.text = pTitle
        myHolder.pDescriptionTv.text = pDescription

        //set user dp
        try {
            Picasso.get().load(uDp).placeholder(R.drawable.ic_default_img).into(myHolder.uPictureIv)
        } catch (e: Exception) {
        }

        //set post image
        if (pImage == "noImage") {
            //hide imageview
            myHolder.pImageIv.visibility = View.GONE
        } else {
            try {
                Picasso.get().load(pImage).into(myHolder.pImageIv)
            } catch (e: Exception) {
            }
        }


        //handle button click
        myHolder.moreBtn.setOnClickListener {
            Toast.makeText(context, "More", Toast.LENGTH_SHORT).show()
        }
        myHolder.likeBtn.setOnClickListener {
            Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show()
        }
        myHolder.commentBtn.setOnClickListener {
            Toast.makeText(
                context,
                "Comment",
                Toast.LENGTH_SHORT
            ).show()
        }
        myHolder.shareBtn.setOnClickListener {
            Toast.makeText(context, "share", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    //view holder class
    inner class MyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //views from row_posts.xml
        var uPictureIv: ImageView
        var pImageIv: ImageView
        var uNameTv: TextView
        var pTimeTv: TextView
        var pTitltTv: TextView
        var pDescriptionTv: TextView
        var pLikesTv: TextView
        var moreBtn: ImageButton
        var likeBtn: Button
        var commentBtn: Button
        var shareBtn: Button

        init {
            uPictureIv = itemView.findViewById(R.id.uPictureIv)
            pImageIv = itemView.findViewById(R.id.pImageIv)
            uNameTv = itemView.findViewById(R.id.uNameTv)
            pTimeTv = itemView.findViewById(R.id.pTimeTv)
            pTitltTv = itemView.findViewById(R.id.pTitleTv)
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv)
            pLikesTv = itemView.findViewById(R.id.pLikesTv)
            moreBtn = itemView.findViewById(R.id.moreBtn)
            likeBtn = itemView.findViewById(R.id.likeBtn)
            commentBtn = itemView.findViewById(R.id.commentBtn)
            shareBtn = itemView.findViewById(R.id.shareBtn)
        }
    }

}
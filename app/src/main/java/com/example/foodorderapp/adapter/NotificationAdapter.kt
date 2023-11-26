package com.example.foodorderapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapp.R
import com.example.foodorderapp.model.NotificationModel

class NotificationAdapter(private val mList:List<NotificationModel>):RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val notification:NotificationModel=mList[position]
        holder.notificationImage.setImageResource(notification.notificationImage)
        holder.notificationText.text=notification.notificationText
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView) {

        val notificationImage:ImageView=itemView.findViewById(R.id.notifiactionimage)
        val notificationText:TextView=itemView.findViewById(R.id.notificationtext)

    }
}
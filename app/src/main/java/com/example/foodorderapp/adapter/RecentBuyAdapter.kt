package com.example.foodorderapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.model.CartItems

class RecentBuyAdapter(private val mLIst:MutableList<CartItems>,private val requireContex:Context):RecyclerView.Adapter<RecentBuyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(requireContex).inflate(R.layout.recent_buy_sample, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item:CartItems=mLIst[position]

        holder.foodName.text=item.foodName
        holder.foodPrice.text="₹"+item.foodPrice
        holder.foodQuantity.text=item.foodQuantity.toString()
//        holder.foodImage.setImageResource(R.drawable.menu1)
        val uri=Uri.parse(item.foodImage.toString())
        Glide.with(requireContex).load(uri).into(holder.foodImage)

    }
    override fun getItemCount(): Int {
      return mLIst.size
    }


    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView){
         val foodImage:ImageView=itemView.findViewById(R.id.imageFood)
        val foodName:TextView=itemView.findViewById(R.id.nameFood)
        val foodQuantity:TextView=ItemView.findViewById(R.id.quantityFood)
        val foodPrice:TextView=itemView.findViewById(R.id.priceTotal)

    }

}
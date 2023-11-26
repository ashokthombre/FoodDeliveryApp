package com.example.foodorderapp.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.BuyAgainItemBinding
import com.example.foodorderapp.databinding.PopulerItemBinding
import com.example.foodorderapp.model.BuyAgainModel
import com.example.foodorderapp.model.CartItems

class BuyAginAdapter(private val mList:MutableList<CartItems>,private val requireContex:Context):RecyclerView.Adapter<BuyAginAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return BuyAginAdapter.ViewHolder(
            BuyAgainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val buyAgain:CartItems=mList[position]

       holder.bind(buyAgain,requireContex)


    }
    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(private val binding:BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(buy: CartItems, requireContex: Context) {

            binding.hotelname.text=""
            binding.foodname.text=buy.foodName
            binding.price.setText("₹ "+buy.foodPrice)
//            binding.foodimage.setImageResource(buy.foodImage)
            val uri=Uri.parse(buy.foodImage.toString())
            Glide.with(requireContex).load(uri).into(binding.foodimage)

        }


    }
}
package com.example.foodorderapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.example.foodorderapp.DetailActivity
import com.example.foodorderapp.R
import com.example.foodorderapp.model.MenuItem
import com.example.foodorderapp.model.Order

class MenuAdapter(private var mList: MutableList<MenuItem>, private val requierdContex: Context) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val menuItem: MenuItem = mList[position]
        holder.foodname.text = menuItem.foodName
        holder.price.text = "₹"+menuItem.foodPrice
        val uri = Uri.parse(menuItem.foodImage)
        Glide.with(requierdContex).load(uri).into(holder.foodimage)


        holder.itemView.setOnClickListener {
            var intent = Intent(requierdContex, DetailActivity::class.java)
            intent.putExtra("description", menuItem.foodDescription)
            intent.putExtra("ingredient", menuItem.foodIngrediant)
            intent.putExtra("imageuri", uri.toString())
            intent.putExtra("foodname", menuItem.foodName)
            intent.putExtra("foodprice",menuItem.foodPrice)
            intent.putExtra("adapter", "MenuAdapter")
            requierdContex.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return mList.size

    }

    fun filterList(filteredlist: ArrayList<MenuItem>) {
       mList=filteredlist
        notifyDataSetChanged()
    }


    class ViewHolder(ItenView: View) : RecyclerView.ViewHolder(ItenView) {

        val foodimage: ImageView = itemView.findViewById(R.id.foodimage)
        val foodname: TextView = itemView.findViewById(R.id.foodname)
        val price: TextView = itemView.findViewById(R.id.price)
        val addtocart: TextView = itemView.findViewById(R.id.addtocart)

    }

}





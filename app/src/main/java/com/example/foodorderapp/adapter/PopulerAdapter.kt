package com.example.foodorderapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.DetailActivity
import com.example.foodorderapp.R
import com.example.foodorderapp.model.MenuItem

class PopulerAdapter(private val mList: List<MenuItem>,private val requirecontex:Context):RecyclerView.Adapter<PopulerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.populer_item, parent, false)
   return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val menuitem=mList[position]

        holder.price.text="₹"+menuitem.foodPrice
        holder.foodname.text=menuitem.foodName

        val uri= Uri.parse(menuitem.foodImage)
        Glide.with(requirecontex).load(uri).into(holder.foodimage)

        holder.itemView.setOnClickListener {
            var intent=Intent(requirecontex,DetailActivity::class.java)
            intent.putExtra("foodname",menuitem.foodName)
            intent.putExtra("foodprice",menuitem.foodPrice)
            intent.putExtra("foodimage",uri.toString())
            intent.putExtra("fooddescription",menuitem.foodDescription)
            intent.putExtra("foodingredient",menuitem.foodIngrediant)
            intent.putExtra("adapter","PopulerAdapter")
            requirecontex.startActivity(intent)
        }



    }
    override fun getItemCount(): Int {
      return mList.size
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView) {

        val foodimage:ImageView=itemView.findViewById(R.id.foodimage)
        val foodname:TextView=itemView.findViewById(R.id.foodname)
        val price:TextView=itemView.findViewById(R.id.price)
        val addtocart:TextView=itemView.findViewById(R.id.addtocart)

    }

}

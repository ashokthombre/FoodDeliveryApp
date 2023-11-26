package com.example.foodorderapp.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.model.CartItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartAdapter(private val mList: MutableList<CartItems>, private val requireContex: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var cartRef:DatabaseReference


    init {

        database= FirebaseDatabase.getInstance()
        auth= Firebase.auth
        cartRef=database.reference.child("user").child(auth.uid!!).child("CartItems")
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cartItem = mList[position]
        holder.foodname.text = cartItem.foodName
        holder.price.text = cartItem.foodPrice
        holder.quantity.text = cartItem.foodQuantity.toString()

        val uri = cartItem.foodImage
        val p = holder.price.text.toString()
        val foodp = p.toDouble()
        Glide.with(requireContex).load(uri.toString()).into(holder.foodImage)
        var count: Int = 1

        holder.plus.setOnClickListener {
            count++
            holder.quantity.text = count.toString()

            holder.price.text = (foodp * count).toString()
            holder.minus.isClickable = true

        }


        holder.minus.setOnClickListener {
            if (holder.quantity.text.toString().equals("0")) {
                holder.minus.isClickable = false
                return@setOnClickListener
            }
            val a = holder.quantity.text.toString()

            count--
            holder.quantity.text = count.toString()
            holder.price.text = (foodp * count).toString()
        }


        holder.trash.setOnClickListener {
          val positionRetrive:Int=position
            getUniqueKeyAtPosition(positionRetrive){uniqueKey ->
                if (uniqueKey!=null)
                {
                   removeItem(position,uniqueKey)
                    mList.removeAt(position)
                }
            }
        }

    }

    private fun removeItem(position: Int, uniqueKey: String) {
         if (uniqueKey!=null)
         {
             cartRef.child(uniqueKey).removeValue().addOnSuccessListener {
                 Toast.makeText(requireContex,"Item Removed",Toast.LENGTH_SHORT).show()
                 notifyItemRemoved(position)
                 notifyItemRangeChanged(position,mList.size)
             }
                 .addOnFailureListener {
                     Toast.makeText(requireContex,"Something went wrong !",Toast.LENGTH_SHORT).show()
                     Log.d("Error",it.toString())
                 }
         }
    }

    private fun getUniqueKeyAtPosition(positionRetrive: Int,onComplete:(String) -> Unit){
      cartRef.addListenerForSingleValueEvent(object :ValueEventListener{
          override fun onDataChange(snapshot: DataSnapshot) {
             var uniqueKey:String?=null
              snapshot.children.forEachIndexed { index, dataSnapshot ->
                  if (index==positionRetrive) {
                      uniqueKey=dataSnapshot.key
                      return@forEachIndexed
                  }
              }
              onComplete(uniqueKey!!)
          }

          override fun onCancelled(error: DatabaseError) {
             Toast.makeText(requireContex,"Something went wromg !",Toast.LENGTH_SHORT).show()
          }

      })
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodimage)
        val price: TextView = itemView.findViewById(R.id.price)
        val foodname: TextView = itemView.findViewById(R.id.foodname)
        val quantity: TextView = itemView.findViewById(R.id.count)
        val plus: TextView = itemView.findViewById(R.id.plus)
        val minus: TextView = itemView.findViewById(R.id.minus)
        val trash: ImageView = itemView.findViewById(R.id.trash)
    }



}


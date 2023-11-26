package com.example.foodorderapp.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.RecentBuyActivity
import com.example.foodorderapp.adapter.BuyAginAdapter
import com.example.foodorderapp.adapter.CartAdapter
import com.example.foodorderapp.databinding.FragmentHistoryBinding
import com.example.foodorderapp.model.BuyAgainModel
import com.example.foodorderapp.model.CartItems
import com.example.foodorderapp.model.Order
import com.example.foodorderapp.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.io.Serializable


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAginAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()
    private var cartItemList: MutableList<CartItems> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        retriveBuyHistory()

        binding.recentCardView.setOnClickListener {
            var intent=Intent(requireContext(),RecentBuyActivity::class.java)
            intent.putExtra("list",cartItemList as Serializable)
            requireContext().startActivity(intent)

        }

        return binding.root
    }

    private fun retriveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser!!.uid

        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery: Query = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val order = buySnapshot.getValue(OrderDetails::class.java)!!
                    listOfOrderItem.add(order)
                    for (list in listOfOrderItem) {
                        val cartList: MutableList<CartItems>? = list.itemList
                        for (l in cartList!!) {
                            Log.d("message", l.foodName.toString())
                            cartItemList.add(l)
                        }

                        setdatatoRecycler()
                    }
                }

                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setDataInRecentBuyItem()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setdatatoRecycler() {
        buyAgainAdapter = BuyAginAdapter(cartItemList, requireContext())
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.historyRecyclerView.adapter = buyAgainAdapter

    }

    private fun setDataInRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOrderItem: OrderDetails = listOfOrderItem.firstOrNull()!!
        val list: CartItems? = recentOrderItem.itemList!!.firstOrNull()
        binding.foodname.text = list!!.foodName
        binding.price.text = "₹" + list.foodPrice
        val uri = Uri.parse(list.foodImage.toString())
        Glide.with(requireContext()).load(uri).into(binding.foodimage)
       if (recentOrderItem.orderAccepted)
       {

           binding.orderRecievedStatus.setBackgroundColor(Color.parseColor("#1fc57A"))
           binding.hotelname.setText("Delivered")
           binding.hotelname.setTextColor(Color.parseColor("#1fc57A"))
       }
        else
       {
           binding.orderRecievedStatus.setBackgroundColor(Color.parseColor("#FFF80202"))
           binding.hotelname.setText("Not Delivered")
           binding.hotelname.setTextColor(Color.parseColor("#FFF80202"))
       }

    }

    companion object {

    }


}
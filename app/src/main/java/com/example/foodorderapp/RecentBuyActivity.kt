package com.example.foodorderapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapp.adapter.RecentBuyAdapter
import com.example.foodorderapp.databinding.ActivityRecentBuyBinding
import com.example.foodorderapp.model.CartItems

class RecentBuyActivity : AppCompatActivity() {
    private val binding:ActivityRecentBuyBinding by lazy {
        ActivityRecentBuyBinding.inflate(layoutInflater)
    }
    private lateinit var listItemCount: MutableList<CartItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listItemCount =intent.getSerializableExtra("list") as MutableList<CartItems>

        listItemCount.run { reverse() }
        val adapter=RecentBuyAdapter(listItemCount, this)
        binding.recentRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.recentRecyclerView.adapter=adapter

        binding.btnBack.setOnClickListener {
            finish()
        }


    }
}
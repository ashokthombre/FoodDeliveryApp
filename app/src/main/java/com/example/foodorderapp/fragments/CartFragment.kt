package com.example.foodorderapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapp.PayOutActivity
import com.example.foodorderapp.adapter.CartAdapter
import com.example.foodorderapp.databinding.FragmentCartBinding
import com.example.foodorderapp.model.CartItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Serializable


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var cartItems: MutableList<CartItems>
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        retrivedata()
        binding.proccedBtn.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                val intent = Intent(requireContext(), PayOutActivity::class.java)
                intent.putExtra("list", cartItems as Serializable)
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please add Some items in cart..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }
    private fun retrivedata() {
        val cartRef: DatabaseReference =
            database.reference.child("user").child(auth.uid!!).child("CartItems")
        cartItems = mutableListOf()
        cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (cartSnapshot in snapshot.children) {
                    val cartItem = cartSnapshot.getValue(CartItems::class.java)
                    cartItems.add(cartItem!!)
                }
                setAdapter()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.d("Error", error.toString())
            }

        })
    }
    private fun setAdapter() {
        adapter = CartAdapter(cartItems, requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
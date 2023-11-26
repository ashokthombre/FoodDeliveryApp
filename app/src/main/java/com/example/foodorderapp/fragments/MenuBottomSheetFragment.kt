package com.example.foodorderapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapp.R
import com.example.foodorderapp.adapter.MenuAdapter
import com.example.foodorderapp.databinding.FragmentMenuBottomSheetBinding
import com.example.foodorderapp.model.MenuItem
import com.example.foodorderapp.model.Order
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuBottomSheetFragment : BottomSheetDialogFragment() {

 private lateinit var binding:FragmentMenuBottomSheetBinding
 private lateinit var database:FirebaseDatabase
 private lateinit var menuItem:MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database= FirebaseDatabase.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding= FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        binding.backbtn.setOnClickListener {
            dismiss()
        }

        retriveMenuItem()


        return binding.root

    }

    private fun retriveMenuItem() {
       val foodRef:DatabaseReference=database.reference.child("menu")
        menuItem= mutableListOf()
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children)
                {
                    val menuItems= foodSnapshot.getValue(MenuItem::class.java)

                    menuItems?.let { menuItem.add(it) }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setAdapter() {
        val adapter=MenuAdapter(menuItem,requireContext())

        binding.menuRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter=adapter

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

    }
}
package com.example.foodorderapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodorderapp.R
import com.example.foodorderapp.adapter.PopulerAdapter

import com.example.foodorderapp.databinding.FragmentHomeBinding
import com.example.foodorderapp.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewmenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "this")
        }


        rettrivedata()



        return binding.root
    }

    private fun rettrivedata() {

        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodsnapshot in snapshot.children) {
                    val menuItem = foodsnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(menuItem) }

                }
                randomPopulerItems()


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

    private fun randomPopulerItems() {
        val index = menuItems.indices.shuffled()
        val numItemtoShow = 6
        val subsetMenuItem = index.take(numItemtoShow).map { menuItems[it] }
        setAdapter(subsetMenuItem)
    }

    private fun setAdapter(subsetMenuItem: List<MenuItem>) {
        val adapter = PopulerAdapter(subsetMenuItem, requireContext())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = adapter

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imamgeSlider = binding.imageSlider
        imamgeSlider.setImageList(imageList)

        imamgeSlider.setImageList(imageList, ScaleTypes.FIT)

        imamgeSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {

                val itemPosition = imageList[position]
                val itemMessage = "Selected image $itemPosition "

                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })

//        val items= listOf<String>("Burger","sandwich","momo","item","Panipuri","Rice")
//        val price= listOf<String>("$5","$7","&8","$10","$12","$13")
//        val images= listOf<Int>(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,R.drawable.menu2,R.drawable.menu5)
//
//        val adapter=PopulerAdapter(items,price,images,requireContext())
//
//        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
//
//        binding.recyclerView.adapter=adapter

    }


}
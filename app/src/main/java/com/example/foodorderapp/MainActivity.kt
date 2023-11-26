package com.example.foodorderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodorderapp.fragments.MenuBottomSheetFragment
import com.example.foodorderapp.fragments.NotificationBottomFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var NavController:NavController=findNavController(R.id.fragmentContainerView)
        var bottom:BottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView);
         bottom.setupWithNavController(NavController)
        var notificationIcon:ImageButton=findViewById<ImageButton>(R.id.notification)

        notificationIcon.setOnClickListener {
            val bottomSheetDialog= NotificationBottomFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")

        }

    }
}
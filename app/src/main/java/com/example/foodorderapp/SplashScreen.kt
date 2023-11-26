package com.example.foodorderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_sacreen)

        Handler(Looper.getMainLooper()).postDelayed({
            val i=Intent(this@SplashScreen,StartActivity::class.java)
             startActivity(i)
            finish()

        },3000)
    }
}
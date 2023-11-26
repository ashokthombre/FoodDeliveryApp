package com.example.foodorderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.foodorderapp.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding:ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList :Array<String> = arrayOf("Pune","Mumbai","Nagpur","Nashik","Kolhapur")

        val adapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, locationList)

        val autocomlpeteTextView: AutoCompleteTextView = binding.listoflocations

        autocomlpeteTextView.setAdapter(adapter)


    }
}
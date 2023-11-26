package com.example.foodorderapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorderapp.MainActivity
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentCongratsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CongratsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentCongratsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentCongratsBottomSheetBinding.inflate(inflater,container,false);

      binding.gohome.setOnClickListener {
         var intent=Intent(requireContext(),MainActivity::class.java)
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          startActivity(intent)

      }


        return binding.root
    }

    companion object {

    }
}
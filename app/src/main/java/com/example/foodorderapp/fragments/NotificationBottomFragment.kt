package com.example.foodorderapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapp.R
import com.example.foodorderapp.adapter.NotificationAdapter

import com.example.foodorderapp.databinding.FragmentNotificationBottomBinding
import com.example.foodorderapp.model.NotificationModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

      binding= FragmentNotificationBottomBinding.inflate(inflater,container,false)



        binding.backBtn.setOnClickListener {
            dismiss()
        }

      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data=ArrayList<NotificationModel>()

        data.add(NotificationModel(R.drawable.sademoji,"Your order is cancelled"))
        data.add(NotificationModel(R.drawable.truck,"Your order is taken to deliver"))
        data.add(NotificationModel(R.drawable.congrats,"Your order is successfully delivered"))
        data.add(NotificationModel(R.drawable.sademoji,"Your order is cancelled"))
        data.add(NotificationModel(R.drawable.truck,"Your order is taken to deliver"))
        data.add(NotificationModel(R.drawable.congrats,"Your order is successfully delivered"))
        data.add(NotificationModel(R.drawable.sademoji,"Your order is cancelled"))
        data.add(NotificationModel(R.drawable.truck,"Your order is taken to deliver"))
        data.add(NotificationModel(R.drawable.congrats,"Your order is successfully delivered"))

        val adapter= NotificationAdapter(data)

        binding.notificatinRecyclerView.layoutManager= LinearLayoutManager(requireContext())

        binding.notificatinRecyclerView.adapter=adapter

    }

    companion object {

    }
}
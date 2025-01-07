package com.chauhan.foodchef.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauhan.foodchef.R
import com.chauhan.foodchef.adapter.NotificationAdapter
import com.chauhan.foodchef.databinding.FragmentBottomNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomNotificationFragment : BottomSheetDialogFragment() {
    private lateinit var adapter: NotificationAdapter
    private lateinit var binding: FragmentBottomNotificationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBottomNotificationBinding.inflate(layoutInflater,container,false)
         val notifications= arrayListOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
         val notificationImages= arrayListOf(R.drawable.sademoji,R.drawable.truck,R.drawable.congrats)
        adapter= NotificationAdapter(notifications,notificationImages)
        binding.bottomNotificationRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.bottomNotificationRecyclerView.adapter=adapter
        return binding.root


    }



}
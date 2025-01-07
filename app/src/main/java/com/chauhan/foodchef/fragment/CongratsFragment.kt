package com.chauhan.foodchef.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chauhan.foodchef.MainActivity
import com.chauhan.foodchef.R
import com.chauhan.foodchef.databinding.ActivityPayOutBinding
import com.chauhan.foodchef.databinding.FragmentCongratsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CongratsFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentCongratsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCongratsBinding.inflate(layoutInflater,container,false)
        binding.goHomeButton.setOnClickListener {
            val intent=Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}


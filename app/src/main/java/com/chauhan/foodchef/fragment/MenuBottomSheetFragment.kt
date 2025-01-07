package com.chauhan.foodchef.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauhan.foodchef.adapter.MenuAdapter
import com.chauhan.foodchef.databinding.FragmentMenuBottomSheetBinding
import com.chauhan.foodchef.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMenuBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.menuButtonBack.setOnClickListener {
            dismiss()
        }
        retrieveMenuItems()

        return binding.root
    }

    private fun retrieveMenuItems() {
//        initialize database
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapShot in snapshot.children){
                    val menuItem = foodSnapShot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it)}
                }
//               once data receive , set to adapter
                 setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun setAdapter(){
        val adapter=MenuAdapter(menuItems,requireContext())
        binding.menuBottomSheetRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.menuBottomSheetRecyclerView.adapter=adapter
    }




    }

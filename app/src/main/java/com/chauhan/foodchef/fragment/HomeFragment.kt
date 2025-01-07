package com.chauhan.foodchef.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauhan.foodchef.R
import com.chauhan.foodchef.adapter.MenuAdapter
import com.chauhan.foodchef.adapter.PopularAdapter
import com.chauhan.foodchef.databinding.FragmentHomeBinding
import com.chauhan.foodchef.model.MenuItem
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var database:FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>
   private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)


        binding.viewMenuItems.setOnClickListener {
            val bottomSheetDialog=MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"test")
        }

        retrieveDisplayPopularItems()
        return binding.root
    }

    private fun retrieveDisplayPopularItems() {
        database= FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuItems= mutableListOf()

//        retrive menu items from the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapShot in snapshot.children){
                    val menuItem = foodSnapShot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it)}
                }
//                display random popular items
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun randomPopularItems() {
//        create a shuffled list of menu items
        val index=menuItems.indices.toList().shuffled()
        val numToShow=8
        val subsetMenuItems=index.take(numToShow).map { menuItems[it] }

        setAdapter(subsetMenuItems)
    }

    private fun setAdapter(subsetMenuItems:List<MenuItem>) {
        val adapter=MenuAdapter(subsetMenuItems,requireContext())
        binding.PopularRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter=adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList=ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3,ScaleTypes.FIT))


        val imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)


    }
}
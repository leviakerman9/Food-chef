package com.chauhan.foodchef.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauhan.foodchef.R
import com.chauhan.foodchef.adapter.MenuAdapter
import com.chauhan.foodchef.databinding.FragmentSearchBinding
import com.chauhan.foodchef.model.MenuItem


class SearchFragment : Fragment() {
    private lateinit var adapter: MenuAdapter
    private lateinit var menuItems:MutableList<MenuItem>
    private lateinit var binding:FragmentSearchBinding
    private val searchFoodName= listOf("burger","sandwich","pasta","soup","cake","chinese","chicken curry")
    private val searchPrice= listOf("$5","$7","$10","$12","56","100","130")
    private val searchFoodImages= listOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,R.drawable.menu5,R.drawable.menu6,R.drawable.menu7)

    private val filterSearchFoodName= mutableListOf<String>()
    private val filterSearchPrice= mutableListOf<String>()
    private val filterSearchFoodImages= mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater,container,false)
         adapter = MenuAdapter(menuItems,requireContext()
       )
        binding.searchRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter=adapter
        setUpSearchView()
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filterSearchFoodName.clear()
        filterSearchPrice.clear()
        filterSearchFoodImages.clear()
        filterSearchFoodName.addAll(searchFoodName)
        filterSearchPrice.addAll(searchPrice)
        filterSearchFoodImages.addAll(searchFoodImages)
        adapter.notifyDataSetChanged()
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               filterMenuItems(query)
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        filterSearchFoodName.clear()
        filterSearchPrice.clear()
        filterSearchFoodImages.clear()

        searchFoodName.forEachIndexed { index, foodName ->
            if(foodName.contains(query,ignoreCase = true)) {
                filterSearchFoodName.add(foodName)
                filterSearchPrice.add(searchPrice[index])
                filterSearchFoodImages.add(searchFoodImages[index])
            }
        }
         adapter.notifyDataSetChanged()
    }


}
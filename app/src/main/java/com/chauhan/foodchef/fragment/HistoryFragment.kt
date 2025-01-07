package com.chauhan.foodchef.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauhan.foodchef.R
import com.chauhan.foodchef.adapter.BuyAgainAdapter
import com.chauhan.foodchef.databinding.BuyAgainBinding
import com.chauhan.foodchef.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: BuyAgainAdapter
    private val buyFoodName= arrayListOf("burger","sandwich","pasta","soup")
    private val buyPrice= arrayListOf("$5","$7","$10","$12")
    private val buyFoodImages= arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHistoryBinding.inflate(layoutInflater,container,false)
        adapter= BuyAgainAdapter(buyFoodName,buyPrice,buyFoodImages)
        binding.historyFragmentRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.historyFragmentRecyclerView.adapter=adapter
        return binding.root
    }

}
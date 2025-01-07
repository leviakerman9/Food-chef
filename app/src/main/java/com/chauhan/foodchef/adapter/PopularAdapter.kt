package com.chauhan.foodchef.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauhan.foodchef.DetailsActivity
import com.chauhan.foodchef.databinding.PopularListBinding

class PopularAdapter(private val items:List<String>,private val images:List<Int>,private val prices:List<String>,private val requireContext: Context):RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val name=items[position]
        val image=images[position]
        val price=prices[position]
        holder.bind(name,image,price)

        holder.itemView.setOnClickListener {
            val intent=Intent(requireContext,DetailsActivity::class.java)
            intent.putExtra("menuItems",name)
            intent.putExtra("menuItemsImage",image)
            requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return items.size
     }

    class PopularViewHolder(private val binding:PopularListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, image: Int, price: String) {
             binding.foodNamePopular.text=name
            binding.foodImagePopular.setImageResource(image)
            binding.foodPricePopular.text=price
        }

    }
}
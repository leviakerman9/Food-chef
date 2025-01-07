package com.chauhan.foodchef.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauhan.foodchef.databinding.BuyAgainBinding

class BuyAgainAdapter(private val buyAgainItems:ArrayList<String>,private val buyAgainItemsPrice:ArrayList<String>,private val buyAgainItemsImage:ArrayList<Int>):RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        return BuyAgainViewHolder(BuyAgainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return buyAgainItems.size
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(position)
     }

    inner class BuyAgainViewHolder(private val binding: BuyAgainBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                buyAgainItemsFoodName.text=buyAgainItems[position]
                buyAgainItemsFoodPrice.text=buyAgainItemsPrice[position]
                buyIAgainItemsFoodImage.setImageResource(buyAgainItemsImage[position])
            }

        }

    }

}
package com.chauhan.foodchef.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.car.ui.toolbar.MenuItem.OnClickListener
import com.bumptech.glide.Glide
import com.chauhan.foodchef.DetailsActivity
import com.chauhan.foodchef.databinding.MenuListBinding
import com.chauhan.foodchef.model.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext:Context
):RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(MenuListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
         holder.bind(position)
    }
    inner class MenuViewHolder(private val binding: MenuListBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    openDetailsActivity(position)
                }

            }

        }


//        set data in to recycler view items name,price,image
        fun bind(position: Int) {
            val menuItem=menuItems[position]
            binding.apply {
                menuFoodName.text=menuItem.foodName
                menuFoodPrice.text=menuItem.foodPrice
                val uri=Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuFoodImage)

            }
        }

    }

    private fun openDetailsActivity(position: Int) {
         val menuItem=menuItems[position]

//        a intent to open details activity and pass data
        val intent=Intent(requireContext,DetailsActivity::class.java).apply {
            putExtra("MenuItemName",menuItem.foodName)
            putExtra("MenuItemImage",menuItem.foodImage)
            putExtra("MenuItemDescription",menuItem.foodDescription)
            putExtra("MenuItemPrice",menuItem.foodPrice)
            putExtra("MenuItemIngredient",menuItem.foodIngredient)
        }
//        start the details activity
        requireContext.startActivity(intent)
    }

}


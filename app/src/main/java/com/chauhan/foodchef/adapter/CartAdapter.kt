package com.chauhan.foodchef.adapter

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chauhan.foodchef.databinding.CartListBinding
import com.chauhan.foodchef.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private val cartDescriptions:MutableList<String>,
    private val cartImages: MutableList<String>,
    private val cartQuantity:MutableList<Int>,
    private val cartIngredients:MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val auth = FirebaseAuth.getInstance()

    init {
//        initialize Firebase
        val database=FirebaseDatabase.getInstance()
        val userId=auth.currentUser?.uid?:""
        val cartItemNumber = cartItems.size
        cartItemQuantity= IntArray(cartItemNumber){1}
        cartItemReference=database.reference.child("user").child(userId).child("CartItems")


    }

    companion object{
        private var cartItemQuantity:IntArray= intArrayOf()
        private lateinit var cartItemReference:DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }


//    get updated quantity

    fun getUpdatedItemQuantities(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity

    }

    inner class CartViewHolder(private val binding: CartListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = cartItemQuantity[position]
                cartFoodName.text = cartItems[position]
                cartFoodPrice.text = cartItemPrices[position]

//                load image using glide
                val uriString = cartImages[position]
                val uri=Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartFoodImage)
                cardItemQuantity.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    val cartItemPosition = adapterPosition
                    if (cartItemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(cartItemPosition)
                    }
                }
            }

        }


        private fun deleteItem(position: Int) {
            val positionRetrieve = position

            getUniqueKeyAtPosition(positionRetrieve){uniqueKey ->
                if  (uniqueKey!= null){
                    removeItem(position,uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
              if(uniqueKey != null){
                  cartItemReference.child(uniqueKey).removeValue().addOnSuccessListener {
                      cartItems.removeAt(position)
                      cartDescriptions.removeAt(position)
                      cartItemPrices.removeAt(position)
                      cartImages.removeAt(position)
                      cartQuantity.removeAt(position)
                      cartIngredients.removeAt(position)
                      Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show()


//                      update item quantities
                      cartItemQuantity= cartItemQuantity.filterIndexed{ index, i -> index!= position }.toIntArray()
                      notifyItemRemoved(position)
                      notifyItemRangeChanged(position,cartItems.size)
                  }.addOnFailureListener {
                      Toast.makeText(context,"Failed To Delete",Toast.LENGTH_SHORT).show()
                  }
              }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int,onComplete:(String) -> Unit) {
            cartItemReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String?=null
//                    loop for snapshot children
                    snapshot.children.forEachIndexed{ index , dataSnapshot ->
                        if(index == positionRetrieve){
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        private fun increaseQuantity(position: Int) {
            if (cartItemQuantity[position] < 10) {
                cartItemQuantity[position]++
                binding.cardItemQuantity.text = cartItemQuantity[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (cartItemQuantity[position] > 10) {
                cartItemQuantity[position]--
                binding.cardItemQuantity.text = cartItemQuantity[position].toString()
            }
        }

    }
}

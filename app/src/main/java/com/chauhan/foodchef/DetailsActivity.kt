package com.chauhan.foodchef

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chauhan.foodchef.databinding.ActivityDetailsBinding
import com.chauhan.foodchef.fragment.MenuBottomSheetFragment
import com.chauhan.foodchef.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DetailsActivity : AppCompatActivity() {
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodPrice: String? = null
    private var foodDescription: String? = null
    private var foodIngredient: String? = null
    private lateinit var auth: FirebaseAuth


    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        initialize firebase auth
        auth = Firebase.auth


        foodName = intent.getStringExtra("MenuItemName")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodIngredient = intent.getStringExtra("MenuItemIngredient")
        foodImage = intent.getStringExtra("MenuItemImage")


        with(binding) {
            detailsFoodName.text = foodName
            detailsShortDescription.text = foodDescription
            detailsIngredient.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailsImageView)
        }


        binding.detailsBackButton.setOnClickListener {
            finish()
        }

        binding.detailsAddToCartButton.setOnClickListener {
            addToCart()
        }
    }

    private fun addToCart() {

        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""
        val cartItem = CartItems(
            foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            foodImage.toString(),
            1,
            foodIngredient.toString()
        )

//          save data to cart item to firebase
            database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
                Toast.makeText(this,"Items Added To Cart Successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Item Not Added",Toast.LENGTH_SHORT).show()
            }

    }
}
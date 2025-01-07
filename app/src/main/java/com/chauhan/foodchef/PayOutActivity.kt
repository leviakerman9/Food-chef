package com.chauhan.foodchef

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chauhan.foodchef.databinding.ActivityPayOutBinding
import com.chauhan.foodchef.fragment.CongratsFragment

class PayOutActivity : AppCompatActivity() {
    private val binding:ActivityPayOutBinding by lazy {
        ActivityPayOutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {
            finish()
        }
        binding.placeMyOrderButton.setOnClickListener {
            val bottomSheetDialog= CongratsFragment()
            bottomSheetDialog.show(supportFragmentManager,"test")
        }
    }
}
package com.chauhan.foodchef

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chauhan.foodchef.databinding.ActivityStartScreenBinding

class StartScreen : AppCompatActivity() {
    private val binding:ActivityStartScreenBinding by lazy{
        ActivityStartScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.chauhan.foodchef

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chauhan.foodchef.databinding.ActivityMainBinding
import com.chauhan.foodchef.fragment.BottomNotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var navController: NavController=findNavController(R.id.fragmentContainerView)
        var bottomNav:BottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)

        binding.notificationButton.setOnClickListener {
            val bottomSheetDialog = BottomNotificationFragment()
            bottomSheetDialog.show(supportFragmentManager, "test")
        }
    }
}
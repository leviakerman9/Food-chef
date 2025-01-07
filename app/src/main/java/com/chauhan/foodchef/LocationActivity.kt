package com.chauhan.foodchef

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.chauhan.foodchef.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {
    private val binding:ActivityLocationBinding by lazy{
        ActivityLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationList:Array<String> = arrayOf("Gwalior","Jabalpur","Indore","Bhopal","Sagar")
        val adapter:ArrayAdapter<String> =
            ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView:AutoCompleteTextView=binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}
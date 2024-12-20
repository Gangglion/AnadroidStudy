package com.example.navigationfragmentsample

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.navigationfragmentsample.databinding.ActivityMainBinding
import com.example.navigationfragmentsample.dsl.DSLActivity
import com.example.navigationfragmentsample.graph.GraphActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnDsl.setOnClickListener {
            startActivity(Intent(this, DSLActivity::class.java))
        }

        binding.btnGraph.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }
    }
}
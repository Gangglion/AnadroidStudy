package com.example.practice_and.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.practice_and.R
import com.example.practice_and.databinding.ActivityExViewBindingBinding

class ExViewBindingActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityExViewBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExViewBindingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.tvEx.apply{
            setOnClickListener {
                mBinding.tvEx.text = "변경된 텍스트"
            }
        }

        mBinding.btnEx.apply{
            setOnClickListener {
                // open Fragment
//                supportFragmentManager.beginTransaction().replace(R.id.frag_view, ExViewBindingFragment()).commit()
                supportFragmentManager.beginTransaction().replace(mBinding.fragContainer.id, ExViewBindingFragment()).commit()
            }
        }
    }
}
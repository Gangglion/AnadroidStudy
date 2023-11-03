package com.example.practice_and.bluetooth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.practice_and.BaseActivity
import com.example.practice_and.R
import com.example.practice_and.databinding.ActivityBluetoothBinding

class BluetoothActivity : BaseActivity() {
    private lateinit var mBinding: ActivityBluetoothBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.bottomMenu.apply{
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.menu_bluetooth ->{
                        changeMenu(BluetoothFragment())
                        true
                    }
                    R.id.menu_ble ->{
                        changeMenu(BleFragment())
                        true
                    }
                    else ->{
                        false
                    }
                }
            }
        }
    }

    private fun changeMenu(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frag_container, fragment).commit()
    }
}
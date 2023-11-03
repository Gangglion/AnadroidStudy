package com.example.practice_and.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_and.App

@SuppressLint("MissingPermission")
class BluetoothViewModel: ViewModel() {
    private var _bluetoothManager: BluetoothManager? = null
    val bluetoothManager get() = _bluetoothManager

    private var _bluetoothAdapter: BluetoothAdapter? = null
    val bluetoothAdapter get() = _bluetoothAdapter

    private var _pariedDevice: Set<BluetoothDevice>? = null

    fun initBluetoothSetting(context: Context){
        _bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        _bluetoothAdapter = _bluetoothManager?.adapter
    }
    fun checkBluetoothEnable(): Int{
        _bluetoothAdapter?.let{
            if(_bluetoothAdapter?.isEnabled == false){ // 블루투스 활성화 확인
                return 1 // 블루투스가 활성화 되어있지 않음
            } else{
                return 0
            }
        }.run{
            // 블루투스를 지원하지 않는 기기
            return 2
        }
    }

    fun findPairedDevice(){
        _pariedDevice = _bluetoothAdapter?.bondedDevices
        _pariedDevice?.forEach {device->
            val deviceName = device.name
            val deviceHardwareAddress = device.address
            Log.v(App.TAG, "Paired :: name : $deviceName\naddress : $deviceHardwareAddress")
        }
    }

    fun findConnectableDevice(){
        _bluetoothAdapter?.startDiscovery()
    }
}
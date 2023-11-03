package com.example.practice_and.bluetooth

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.practice_and.App
import com.example.practice_and.databinding.FragmentBluetoothBinding

class BluetoothFragment : Fragment() {
    private lateinit var mBinding: FragmentBluetoothBinding
    private val mBluetoothViewModel: BluetoothViewModel by viewModels()
    private lateinit var mContext: Context
    private lateinit var mParentActivity: Activity
    private var isAllow: Boolean = false

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){results ->
        if(results[ACCESS_COARSE_LOCATION]!! && results[BLUETOOTH_CONNECT]!! && results[BLUETOOTH_SCAN]!!){
            this.isAllow = true
            Toast.makeText(mContext, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
            processBluetoothState()
        } else{
            this.isAllow = false
            Toast.makeText(mContext, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestBtEnable = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        processBluetoothState()
    }

    private val receiver = object : BroadcastReceiver(){
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action!!){
                BluetoothDevice.ACTION_FOUND ->{
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address

                    Log.v(App.TAG, "Find :: name : $deviceName\naddress : $deviceHardwareAddress")
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mParentActivity = activity as BluetoothActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBluetoothBinding.inflate(inflater)
        // 블루투스 초기화
        mBluetoothViewModel.initBluetoothSetting(mContext)

        // 리시버 등록
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireActivity().registerReceiver(receiver, filter)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        val permissionList = arrayOf(ACCESS_COARSE_LOCATION, BLUETOOTH_CONNECT, BLUETOOTH_SCAN)
        requestPermission.launch(permissionList)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(receiver)
    }

    private fun processBluetoothState(){
        when(mBluetoothViewModel.checkBluetoothEnable()){
            0 ->{
                Log.d(App.TAG, "블루투스 탐색 시작")
                mBluetoothViewModel.findPairedDevice()
                mBluetoothViewModel.findConnectableDevice()
            }
            1 ->{
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                requestBtEnable.launch(enableBtIntent)
            }
            2->{
                Toast.makeText(mContext, "블루투스를 지원하지 않는 기기입니다. 메인화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
                mParentActivity.finish()
            }
        }
    }
}
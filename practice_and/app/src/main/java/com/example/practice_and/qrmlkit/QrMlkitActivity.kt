package com.example.practice_and.qrmlkit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practice_and.App
import com.example.practice_and.CustomSnackBar
import com.example.practice_and.databinding.ActivityQrmlkitBinding
import com.example.practice_and.qrzxing.QrZxingActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QrMlkitActivity : AppCompatActivity() {
    companion object {
        const val CAMERA_PERMISSION = 2
    }

    private lateinit var mContext: Context
    private lateinit var mBinding: ActivityQrmlkitBinding
    private var permissions = arrayOf(Manifest.permission.CAMERA)
    private var isPermissionGranted = false

    private lateinit var cameraExecutor: ExecutorService

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty())
            isPermissionGranted = true
        else {
            CustomSnackBar.make(mBinding.root, "권한이 설정되지 않았습니다.", "권한허용").show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityQrmlkitBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mContext = this

        if (ActivityCompat.checkSelfPermission(
                mContext,
                permissions[0]
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionGranted = true
            cameraExecutor = Executors.newSingleThreadExecutor()
        } else {
            requestPermissions(permissions, QrZxingActivity.CAMERA_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPermissionGranted) {
             startCamera()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(mBinding.pvArea.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (e: Exception) {
                Log.e(App.TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }
}
package com.example.practice_and.qrzxing

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.practice_and.CustomSnackBar
import com.example.practice_and.databinding.ActivityQrzxingBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QrZxingActivity : AppCompatActivity() {
    companion object {
        const val CAMERA_PERMISSION = 2
    }

    private lateinit var mContext: Context
    private lateinit var mBinding: ActivityQrzxingBinding
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

    private val barcodeLauncher = registerForActivityResult(ScanContract()){
        result: ScanIntentResult ->
        run {
            if (result.contents == null) {
                Toast.makeText(mContext, "Canceled", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // Toast.makeText(mContext, "Scanned: ${result.contents}", Toast.LENGTH_SHORT).show()
                QrSnackbar.make(mBinding.root, result.contents).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityQrzxingBinding.inflate(layoutInflater)
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
            requestPermissions(permissions, CAMERA_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPermissionGranted) {
            // startCamera()
            // SCAN 실행
            val options = ScanOptions()
            options.setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }
    }

//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder()
//                .build()
//                .also{
//                    it.setSurfaceProvider(mBinding.pvArea.surfaceProvider)
//                }
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try{
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
//            } catch(e: Exception){
//                Log.e(App.TAG, "Use case binding failed", e)
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
}
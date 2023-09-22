package com.example.practice_and.qrmlkit

import android.graphics.ImageFormat.YUV_420_888
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.practice_and.App
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer(
    private val onQrCodesDetected: (qrCode: Result) -> Unit
) : ImageAnalysis.Analyzer {
    private val yuvFormats = mutableListOf(YUV_420_888)

    init {
        yuvFormats.addAll(listOf(YUV_420_888, YUV_420_888))
    }
    private val reader = MultiFormatReader().apply {
        val map = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(imageProxy: ImageProxy) {
        val data = imageProxy.planes[0].buffer.toByteArray()
        if (imageProxy.format !in yuvFormats) {
            Log.e(App.TAG, "Excepted YUV, now = ${imageProxy.format}")
            return
        }
        val source = PlanarYUVLuminanceSource(
            data,
            imageProxy.width,
            imageProxy.height,
            0,
            0,
            imageProxy.width,
            imageProxy.height,
            false
        )
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            // Whenever reader fails to detect a QR code in image
            // it throws NotFoundException
            val result = reader.decode(binaryBitmap)
            onQrCodesDetected(result)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        imageProxy.close()
    }
}
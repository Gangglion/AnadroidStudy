package com.example.practice_and.googlefitness

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import com.example.practice_and.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.OnDataPointListener

class FitnessFragment : Fragment() {
    enum class FitActionRequestCode{
        FIND_DATA_SOURCES
    }

    companion object{
        const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 9999
    }
    private lateinit var mContext: Context
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
        .addDataType(DataType.TYPE_HEIGHT)
        .addDataType(DataType.TYPE_WEIGHT)
        .build()

    private val runningQOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private var dataPointListener: OnDataPointListener?= null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fitness, container, false)

        view.findViewById<AppCompatButton>(R.id.btn_login).apply{
            setOnClickListener {
                checkPermissionsAndRun(FitActionRequestCode.FIND_DATA_SOURCES)
            }
        }
        return view
    }

    private fun checkPermissionsAndRun(fitActionRequestCode: FitActionRequestCode){
        if(permissionApproved()){
            fitSignIn(fitActionRequestCode)
        } else{
            requestRuntimePermissions(fitActionRequestCode)
        }
    }

    private fun fitSignIn(requestCode: FitActionRequestCode){
        if(oAuthPermissionsApproved()){
            Log.i("shhan", "권한이 등록되어 있음. 권한 나오는 화면 부분만 체크하면 됨")
        } else{
            requestCode.let{
                GoogleSignIn.requestPermissions(
                    this,
                    it.ordinal,
                    getGoogleAccount(), fitnessOptions
                )
            }
        }
    }

    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(getGoogleAccount(), fitnessOptions)

    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(mContext, fitnessOptions)

    private fun permissionApproved(): Boolean{
        val approved = if(runningQOrLater){
            PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                mContext, Manifest.permission.ACTIVITY_RECOGNITION
            )
        } else{
            true
        }

        return approved
    }

    private fun requestRuntimePermissions(requestCode: FitActionRequestCode, ){
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity as Activity, Manifest.permission.ACTIVITY_RECOGNITION)

        requestCode.let{
            if (shouldProvideRationale) {
                Log.i("shhan", "Displaying permission rationale to provide additional context.")
            } else {
                Log.i("shhan", "Requesting permission")
                // Request permission. It's possible this can be auto answered if device policy
                // sets the permission in a given state or the user denied the permission
                // previously and checked "Never ask again".
                ActivityCompat.requestPermissions(activity as Activity,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    requestCode.ordinal)
            }
        }
    }
}
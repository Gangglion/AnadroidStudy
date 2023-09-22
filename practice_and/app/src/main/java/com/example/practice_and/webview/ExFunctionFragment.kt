package com.example.practice_and.webview

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.databinding.FragmentExFunctionBinding
import com.example.practice_and.getCalDay
import com.example.practice_and.getDayOfWeekKO
import com.example.practice_and.toDate
import com.example.practice_and.toString
import com.example.practice_and.toTimestamp
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.S)
class ExFunctionFragment : Fragment() {
    private lateinit var mBinding: FragmentExFunctionBinding
    private lateinit var mContext: Context
    private lateinit var mWebviewActivity: WebviewActivity
    private var isDoubleChecked = false

    private val addBackPressed = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if(isDoubleChecked){
                Toast.makeText(mContext, mContext.getString(R.string.close_webview_ready), Toast.LENGTH_SHORT).show()
            } else{
                Handler(Looper.getMainLooper()).postDelayed({isDoubleChecked = true}, 2000)
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mWebviewActivity = activity as WebviewActivity
        requireActivity().onBackPressedDispatcher.addCallback((addBackPressed))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentExFunctionBinding.inflate(inflater, container, false)
        val view = mBinding.root

        mBinding.tvNowstr.text = LocalDateTime.now().toString(App.DATE_FORMAT)
        mBinding.tvNowdate.text = LocalDateTime.now().toDate().toString()
        mBinding.tvNowtimestamp.text = LocalDateTime.now().toTimestamp().toString()
        mBinding.tvDayOfWeek.text = LocalDateTime.now().getDayOfWeekKO()
        mBinding.btnCal.setOnClickListener {
            mBinding.tvResultDay.text = LocalDateTime.now().getCalDay((mBinding.etWantDay.text.toString().toLong())).toString()
        }
        mBinding.btnMain.setOnClickListener { mWebviewActivity.callWebviewFragment() }

        return view
    }
}
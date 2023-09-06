package com.example.practice_and.fragment

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
import com.example.practice_and.R
import com.example.practice_and.activity.WebviewActivity
import com.example.practice_and.databinding.FragmentErrorPageBinding

@RequiresApi(Build.VERSION_CODES.S)
/**
 * A simple [Fragment] subclass.
 * Use the [ErrorPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ErrorPageFragment : Fragment() {
    private lateinit var mBinding : FragmentErrorPageBinding
    private lateinit var mContext : Context
    private lateinit var mWebviewActivity : WebviewActivity
    private var isDoubleBackPressed = false // 뒤로가기 두번 여부 check
    private val addBackPressedCallback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            setBackPressedFunction()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mWebviewActivity = activity as WebviewActivity
        requireActivity().onBackPressedDispatcher.addCallback(addBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentErrorPageBinding.inflate(inflater,container, false)
        val view = mBinding.root

        mBinding.btnMain.setOnClickListener {
            mWebviewActivity.callWebviewFragment()
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        addBackPressedCallback.remove()
    }

    private fun setBackPressedFunction() {
        if(isDoubleBackPressed){
            mWebviewActivity.exitWebview()
        } else{
            Toast.makeText(mContext, mContext.getString(R.string.close_webview_ready), Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({isDoubleBackPressed = true}, 2000)
        }
    }
}
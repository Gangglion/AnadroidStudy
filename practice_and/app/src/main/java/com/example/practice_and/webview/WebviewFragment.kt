package com.example.practice_and.webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.databinding.FragmentWebviewBinding

@RequiresApi(Build.VERSION_CODES.S)
class WebviewFragment : Fragment(), View.OnClickListener,
    AndroidBridge.OnRequestToast {
    // 데이터 바인딩 적용
    private val mUrl = "https://www.naver.com" // 예시 url
    private var isDoubleBackPressed = false // 뒤로가기 두번 여부 check
    private lateinit var mContext: Context
    private lateinit var mWebviewActivity: WebviewActivity
    private lateinit var mBinding: FragmentWebviewBinding

    private val bridge = AndroidBridge()

    // BackPressed 동작 선언
    private val addBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setBackPressedFunction()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mWebviewActivity = activity as WebviewActivity
        requireActivity().onBackPressedDispatcher.addCallback(addBackPressedCallback)

        WebView.setWebContentsDebuggingEnabled(true); // Webview 디버깅 활성화 - BridgeTest
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentWebviewBinding.inflate(inflater, container, false)
        val view = mBinding.root

        mBinding.btnRefresh.setOnClickListener(this)
        mBinding.btnExtentionFunction.setOnClickListener(this)

        mBinding.wvNaver.apply {
            loadUrl(mUrl) // 메인 네이버 open
            settings.javaScriptEnabled = true // 자바스크립트 사용 설정
            settings.useWideViewPort = false // 뷰포트모드 허용
            settings.loadWithOverviewMode = true // 화면 크기에 맞추도록 설정
            settings.setSupportZoom(true) // 핀치줌모드 허용
            settings.builtInZoomControls = true
            settings.displayZoomControls = false // 줌 컨트롤러 안보이게 세팅
            settings.textZoom = 100 // 시스템 글꼴 크기에 의해 글꼴 크기가 변하는것 방지
            webChromeClient = WebChromeClient()
            addJavascriptInterface(bridge, "AndroidBridge") // 브릿지 추가. 웹에서의 호출명 AndroidBridge. "" 안에 있는게 호출명이 된다. 실제 호출할때는 window.AndroidBridge.정의된함수() 로 한다.
            bridge.setListener(this@WebviewFragment)
            webViewClient = object : WebViewClient() { // 객체 표현식으로 오버라이드 함수 재정의
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val nowUrl = request!!.url
                    Log.d(App.TAG, "nowUrl : $nowUrl")
                    // "r" 검색하면 특정 URL로 이동하고, 해당 URL일때 권한 설정하게끔 하면 브릿지 필요 없을듯
                    if(nowUrl.toString() == "https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=r"){
                        Toast.makeText(mContext, "특정 URL일때 권한 요청", Toast.LENGTH_SHORT).show()
                    }
                    view!!.loadUrl(request!!.url.toString()) // 새로운 url 요청이 있을때마다 webview에 url load / ex) 네이버 메인에서 다른 화면으로 이동할 때
                    return true
                }

                override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: Bitmap?
                ) { // 페이지 로딩 시작할때 시작
                    super.onPageStarted(view, url, favicon)
                    mBinding.pbLoading.visibility = View.VISIBLE // 프로그래스바 활성화
                }

                override fun onPageFinished(view: WebView?, url: String?) { // 페이지 로딩 끝났을때
                    mBinding.pbLoading.visibility = View.GONE // 프로그래스바 비활성화
                }

                // 통신 에러 났을 때 에러페이지 처리
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    mWebviewActivity.callErrorFragment()
                }
            }

            // 웹뷰 최하단 도달 확인
            mBinding.wvNaver.setOnScrollChangeListener { _, _, _, _, _ ->
                with(mBinding.wvNaver) {
                    val isDownDirectionPossible = this.canScrollVertically(1)
                    if(!isDownDirectionPossible) {
                        Log.d("glion", "Reached Bottom")
                    }
                }
            }
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        addBackPressedCallback.remove()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_refresh -> {
                mBinding.wvNaver.reload()
            }
            R.id.btn_extention_function ->{
                mWebviewActivity.callExFuncFragment()
            }
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED){
                // 권한 요청
                mWebviewActivity.requestPermission()
            } else{
                // 넘어가
            }
        }
    }

    override fun setAlarm() {
        TODO("Not yet implemented")
    }

    // 뒤로가기 할 때 웹뷰 뒤로갈 페이지 있으면 뒤로가고, 없으면 종료 멘트 이후 종료
    private fun setBackPressedFunction() {
        if (mBinding.wvNaver.canGoBack()) {
            mBinding.wvNaver.goBack()
        } else if (isDoubleBackPressed) {
            mWebviewActivity.exitWebview()
        } else {
            isDoubleBackPressed = true
            Toast.makeText(mContext, mContext.getString(R.string.close_webview_ready), Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({ isDoubleBackPressed = false }, 2000)
        }
    }
}
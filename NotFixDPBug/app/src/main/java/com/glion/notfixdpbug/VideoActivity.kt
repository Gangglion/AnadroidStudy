package com.glion.notfixdpbug

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ProgressBar
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ForwardingPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource

class VideoActivity : BaseActivity() {
    private lateinit var mPlayer: ExoPlayer
    private lateinit var mPlayerView: PlayerView
    private lateinit var mContext: Context
    private lateinit var mHandler: Handler
    private lateinit var mForwardingPlayer: ForwardingPlayer
    private lateinit var mProgressBar: ProgressBar
    private var mIsPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        mContext = this

        mHandler = Handler(Looper.getMainLooper())

        mPlayerView = findViewById(R.id.player_view)
        mPlayer = ExoPlayer.Builder(mContext).setSeekBackIncrementMs(1000L).build()
        mForwardingPlayer = object : ForwardingPlayer(mPlayer){
            override fun isCommandAvailable(command: Int): Boolean {
                return if(command == COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM){
                    false
                } else{
                    super.isCommandAvailable(command)
                }
            }

            override fun getAvailableCommands(): Player.Commands {
                return super.getAvailableCommands()
                    .buildUpon()
                    .remove(COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM)
                    .build()
            }
        }

        mPlayerView.player = mForwardingPlayer
        mPlayerView.keepScreenOn = true

        mPlayer.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState){
                    2 ->{ // 버퍼링
                        Log.d(TAG, "playbackState : $playbackState")
                        Log.d(TAG, "$playbackState : ${resources.configuration.densityDpi}")
                    }
                    3 ->{ // Ready
                        Log.d(TAG, "playbackState : $playbackState")
                        Log.d(TAG, "$playbackState : ${resources.configuration.densityDpi}")
                    }
                }
                if(!mPlayer.playWhenReady){
                    mHandler.removeCallbacks(updateProgressAction)
                } else{
                    onProgress()
                }
            }
        })
        mProgressBar = findViewById(R.id.pv_wait)
        mProgressBar.visibility = View.VISIBLE

        val thread = InsteadNetworkThread()
        thread.start()

        Log.v(TAG, "VideoActivity - onCreate : ${resources.configuration.densityDpi}")
    }

    override fun onBackPressed() {
        exitVideo()
    }

    private fun playVideo(){
        val uri = Uri.parse("android.resource://com.example.practice_and/${R.raw.test_video}")
        val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSource.Factory(mContext)).createMediaSource(MediaItem.fromUri(uri))
        mPlayer.setMediaSource(mediaSource)
        mPlayer.prepare()
        mPlayerView.useController = true
        mProgressBar.visibility = View.GONE
        mPlayer.play()
        mIsPlaying = true
    }

    private val updateProgressAction = Runnable {
        onProgress()
    }

    private fun onProgress(){
        Log.d(TAG, "ExoPlayer State onProgress : ${resources.configuration.densityDpi}")
        val player = mPlayer
        mHandler.removeCallbacks(updateProgressAction)
        val playbackState = player.playbackState
        if(playbackState == Player.STATE_ENDED){
            mHandler.removeCallbacks(updateProgressAction)
            DialogTextOneButton(mContext, object : DialogTextOneButton.OnConfirmClick{
                override fun clickConfirm() {
                    finish()
                }
            }).show()
        } else if(playbackState != Player.STATE_IDLE){

        }
        mHandler.postDelayed(updateProgressAction, 1000)
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "VideoActivity - onStart : ${resources.configuration.densityDpi}")
    }

    override fun onResume() {
        super.onResume()
        if(mIsPlaying){
            Log.d(TAG, "VideoActivity - onCreate : ${resources.configuration.densityDpi}")
            mPlayer.prepare()
            mPlayer.play()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "VideoActivity - onPause : ${resources.configuration.densityDpi}")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "VideoActivity - onStop : ${resources.configuration.densityDpi}")
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.release()
        mHandler.removeCallbacks(updateProgressAction)
        Log.v(TAG, "VideoActivity - onDestroy : ${resources.configuration.densityDpi}")
    }
    private fun setFullScreen(){
        supportActionBar?.hide()
        setTheme(R.style.Theme_FullScreenTheme)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars())
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private fun setDefaultScreen(){
        supportActionBar?.show()
        setTheme(R.style.Theme_NotFixDPBug)
    }

    private fun exitVideo(){
        DialogTextOneButton(mContext, object : DialogTextOneButton.OnConfirmClick{
            override fun clickConfirm() {
                mPlayer.stop()
                mHandler.removeCallbacks(updateProgressAction)
                finish()
            }
        }).show()
    }

    inner class InsteadNetworkThread: Thread(){
        override fun run() {
            Handler(Looper.getMainLooper()).post{
                setFullScreen()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                sleep(100)
                // 다운로드 이미 완료되어 동영상을 재생한다고 가정
                playVideo()
            }
        }
    }
}
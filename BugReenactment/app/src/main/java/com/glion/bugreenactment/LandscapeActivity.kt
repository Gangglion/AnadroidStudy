package com.glion.bugreenactment

import android.content.Context
import android.content.Intent
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
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ForwardingPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class LandscapeActivity : BaseActivity() {
    private lateinit var mPlayer: ExoPlayer
    private lateinit var mPlayerView: PlayerView
    private lateinit var mContext: Context
    private lateinit var mHandler: Handler
    private lateinit var mForwardingPlayer: ForwardingPlayer
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landscape)
        mContext = this

        mHandler = Handler(Looper.getMainLooper())

        mPlayerView = findViewById(R.id.player_view)
        mPlayer = ExoPlayer.Builder(mContext).setSeekBackIncrementMs(100000L).build()
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
                if(mPlayer.playWhenReady){
                    onProgress()
                } else{
                    mHandler.removeCallbacks(updateProgressAction)
                }
            }
        })
        mProgressBar = findViewById(R.id.pv_wait)
        mProgressBar.visibility = View.VISIBLE
        setFullScreen()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        mProgressBar.apply{
            Handler(Looper.getMainLooper()).postDelayed({
                mProgressBar.visibility = View.GONE
                playVideo()
            }, 1000L)
        }
        Log.v(TAG, "LandscapeActivity - onCreate")

        Log.d(TAG, "LandscapeActivity : ${resources.configuration.densityDpi}")
    }

    private fun playVideo(){
        val uri = Uri.parse("android.resource://com.example.practice_and/${R.raw.test_video}")
        val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSource.Factory(mContext)).createMediaSource(MediaItem.fromUri(uri))
        mPlayer.setMediaSource(mediaSource)
        mPlayer.prepare()
        mPlayerView.useController = true
        mPlayer.play()
    }

    private val updateProgressAction = Runnable {
        onProgress()
    }

    private fun onProgress(){
        mHandler.removeCallbacks(updateProgressAction)
        Log.d(TAG, "ExoPlayer State Playing : ${resources.configuration.densityDpi}")
        mHandler.postDelayed(updateProgressAction, 1000)
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "LandscapeActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "LandscapeActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "LandscapeActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "LandscapeActivity - onStop")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "newConfig in LandscapeActivity : ${newConfig.densityDpi}")
    }

    override fun onDestroy() {
        super.onDestroy()
//        startActivity(Intent(mContext, MainActivity::class.java))
        mPlayer.release()
        mHandler.removeCallbacks(updateProgressAction)
        Log.v(TAG, "LandscapeActivity - onDestroy")
    }

    @Suppress("DEPRECATION")
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
        setTheme(R.style.Theme_BugReenactment)
    }
}
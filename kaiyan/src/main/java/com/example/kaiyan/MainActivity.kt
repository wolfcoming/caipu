package com.example.kaiyan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kaiyan.widget.media.AndroidMediaController
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {
    private var mSettings: Settings? = null
    private var mMediaController: AndroidMediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSettings = Settings(this)
        mMediaController = AndroidMediaController(this, false)
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
          video.setMediaController(mMediaController)
        video.setVideoPath("http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear1/prog_index.m3u8")
        button.setOnClickListener {
            play()
        }
    }

    fun play(){
//        video.start()
        startActivity(Intent(this@MainActivity,KaiYanAcitivity::class.java))
    }
}

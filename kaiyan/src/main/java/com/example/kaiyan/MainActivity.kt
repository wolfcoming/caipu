package com.example.kaiyan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kaiyan.widget.media.AndroidMediaController
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            play()
        }

        button2.setOnClickListener {
            root.requestLayout()
        }



    }

    fun play() {
        startActivity(Intent(this@MainActivity, KaiYanAcitivity::class.java))
//        button.layout(button.left, button.top - 100, button.right, button.bottom - 100)
    }
}

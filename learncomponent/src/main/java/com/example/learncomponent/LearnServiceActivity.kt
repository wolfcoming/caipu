package com.example.learncomponent

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.learncomponent.service.MyService
import kotlinx.android.synthetic.main.activity_learnservice.*

class LearnServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learnservice)
        val sercice = Intent(this, MyService::class.java)

        btn1.setOnClickListener {
            startService(sercice)
        }

        btn2.setOnClickListener {
            stopService(sercice)
        }
    }
}
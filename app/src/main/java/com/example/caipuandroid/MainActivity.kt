package com.example.caipuandroid

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.caipuandroid.ui.CategoryActivity
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActiviy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeStateBarColor(Color.RED)
        btnCategory.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

    }
}

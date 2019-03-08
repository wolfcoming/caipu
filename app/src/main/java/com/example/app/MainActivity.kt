package com.example.app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.caipuandroid.ui.CategoryActivity
import com.example.shopingmodule.ui.ShopMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCaipu.setOnClickListener {
            startActivity(Intent(this@MainActivity,CategoryActivity::class.java))
        }

        btnShop.setOnClickListener {
            startActivity(Intent(this@MainActivity,ShopMainActivity::class.java))
        }
    }
}

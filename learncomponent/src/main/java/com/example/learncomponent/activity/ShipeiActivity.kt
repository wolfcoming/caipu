package com.example.learncomponent.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.learncomponent.R
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.utils.FileUtils
import com.infoholdcity.baselibrary.utils.SPUtils
import kotlinx.android.synthetic.main.activity_shipei.*
import okhttp3.OkHttpClient
import okhttp3.Request

class ShipeiActivity : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipei)
        save1.setOnClickListener {
            SPUtils.getInstance(this).put("name2","yqqq")
        }

        save2.setOnClickListener {
            FileUtils.writeFileToSDCardAppend("添加内容","1测试Q","aa.txt")
        }

        btnget.setOnClickListener {
        }

    }







}
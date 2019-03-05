package com.example.caipuandroid

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.example.caipuandroid.ui.CategoryActivity
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_main.*
import com.example.caipuandroid.test.LocationUtils
import com.infoholdcity.baselibrary.utils.StatusBarHelper
import com.tbruyelle.rxpermissions2.RxPermissions


class MainActivity : BaseActiviy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarHelper.setStatusTextColor(true, this)
        StatusBarHelper.setStautsBarColor(this, Color.WHITE,0)
        btnCategory.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        btnDb.setOnClickListener {
            RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe { it ->
//                    Klog.e(contents = "开始定位")
//                    LocationUtils.getInstance(this@MainActivity)
                }

        }
    }
}

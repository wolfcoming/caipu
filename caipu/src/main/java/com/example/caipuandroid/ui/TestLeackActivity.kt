package com.example.caipuandroid.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.infoholdcity.baselibrary.base.BaseActiviy

class TestLeackActivity : BaseActiviy() {
    companion object {
        var demo: Demo? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tv = TextView(this)
        tv.setText("测试内存泄漏")
        tv.setBackgroundColor(Color.WHITE)
        tv.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        tv.gravity = Gravity.CENTER
        setContentView(tv)
        if (demo == null)
            demo = Demo()

        finish()


    }


    inner class Demo() {}
}
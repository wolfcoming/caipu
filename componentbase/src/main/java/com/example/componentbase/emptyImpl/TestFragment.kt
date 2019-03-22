package com.example.componentbase.emptyImpl

import android.view.View
import com.example.componentbase.R
import com.infoholdcity.baselibrary.base.BaseFragment

class TestFragment:BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragment_test
    }

    override fun initView(anchor: View) {
    }
}
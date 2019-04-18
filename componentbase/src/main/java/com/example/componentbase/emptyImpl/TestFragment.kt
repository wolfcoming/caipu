package com.example.componentbase.emptyImpl

import android.view.View
import com.example.componentbase.R
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment:BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragment_test
    }

    override fun initView(anchor: View) {
        btnAddress.setOnClickListener {
            toast("shit")
        }
    }
}
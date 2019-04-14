package com.example.caipuandroid.ui.fragment

import android.view.View
import com.example.caipuandroid.R
import com.example.componentbase.eventbus.SlideMenuEvent
import com.infoholdcity.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.caipu_frgm_category.*
import org.greenrobot.eventbus.EventBus

class Caipu_Frgm_Home :BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.caipu_frgm_category
    }

    override fun initView(anchor: View) {
        tv.setOnClickListener {
            //开启侧滑
            EventBus.getDefault().post(SlideMenuEvent(1))
        }

    }

}
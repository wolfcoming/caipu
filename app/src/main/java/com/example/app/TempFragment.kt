package com.example.app

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.fragment_temp.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TempFragment :BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragment_temp
    }

    override fun initView(anchor: View) {
        btnCaipu.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_HOME).navigation()
        }

        btnShop.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_SHOP_HOME).navigation();
        }
        btnUser.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_USER_LOGIN).navigation();
        }

        GlobalScope.launch(Dispatchers.Main){

        }
    }

}
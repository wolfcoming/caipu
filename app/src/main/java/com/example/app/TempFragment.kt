package com.example.app

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.app.test.TcpClient
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.fragment_temp.*

class TempFragment : BaseFragment() {
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
//            startActivity(Intent(context, TcpClient::class.java))
        }


    }


}
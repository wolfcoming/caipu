package com.example.caipuandroid

import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.componentbase.ServiceFactory
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.caipu_activity_main.*
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.utils.StatusBarHelper

@Route(path = ARouterConfig.ACT_CAIPU_HOME)
class CaipuMainActivity : BaseActiviy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.caipu_activity_main)
        StatusBarHelper.setStatusTextColor(true, this)
        StatusBarHelper.setStautsBarColor(this, Color.WHITE, 0)
        btnCategory.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_CATEGORY).navigation()
        }

        btnADD.setOnClickListener {
            if (!ServiceFactory.instance.getUsercenterService().isLogin()) {
                toast("用户尚未登录")
                ARouter.getInstance().build(ARouterConfig.ACT_USER_LOGIN).navigation()
            } else
                ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_ADD).navigation()
        }
    }
}

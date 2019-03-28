package com.example.userCentercomponent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.utils.SPUtils
import kotlinx.android.synthetic.main.usercenter_activity_main.*

@Route(path = ARouterConfig.ACT_USER_LOGIN)
class LoginMainActivity : BaseActiviy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usercenter_activity_main)
        tvRegister.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_USER_GEGISTER).navigation()
        }


//        getLoginStatue()
//
//        btnLogin.setOnClickListener {
//            val userBeanVo = UserBeanVo()
//            userBeanVo.name = "测试姓名"
//            userBeanVo.userId = "11111111"
//            SPUtils.getInstance(this@LoginMainActivity).putObject("userBean", userBeanVo)
//            getLoginStatue()
//        }
//
//        btnLoginOut.setOnClickListener {
//            SPUtils.getInstance(this@LoginMainActivity).putObject("userBean", null)
//            getLoginStatue()
//        }


    }

    fun getLoginStatue() {
//        val userBeanVo = SPUtils.getInstance(this).getObject<UserBeanVo>("userBean")
//        if (userBeanVo != null) {
//            tvStatus.text = "登录状态： ${userBeanVo.name}登录"
//        } else {
//            tvStatus.text = "暂无登录"
//        }
    }
}

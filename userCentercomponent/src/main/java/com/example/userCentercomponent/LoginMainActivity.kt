package com.example.userCentercomponent

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.componentbase.common.CommonFrgmActivity
import com.example.componentbase.eventbus.UserEvent
import com.example.userCentercomponent.service.impl.UserCenterServiceImpl
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.utils.MD5Utils
import com.infoholdcity.baselibrary.view.SingleProgressDialog
import kotlinx.android.synthetic.main.usercenter_activity_main.*
import org.greenrobot.eventbus.EventBus

@Route(path = ARouterConfig.ACT_USER_LOGIN)
class LoginMainActivity : BaseActiviy() {


    val userService by lazy { UserCenterServiceImpl() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usercenter_activity_main)


        tvRegister.setOnClickListener {
//            CommonFrgmActivity.startCommonFrgmActivity(this, "用户中心", MinFragment::class.java.name, null)
            ARouter.getInstance().build(ARouterConfig.ACT_USER_GEGISTER).navigation()
        }


        btnLogin.setOnClickListener {
            val name = etName.text.toString()
            val pwd = etPwd.text.toString()
            if (name.isBlank() || pwd.isBlank()) {
                toast("用户信息不完整")
                return@setOnClickListener
            }
            val pwdMd5 = MD5Utils.getMD5Str(pwd)

            SingleProgressDialog.showLoading(this)
            userService.login(name, pwdMd5)
                .excute()
                .subscribe({
                    if (it != null) {
                        UserOperation.userLoginSuccess(this@LoginMainActivity, it)
                        SingleProgressDialog.hideLoading()
                        finish()
                    }
                }, {
                    toast(ExceptionHandle.handleException(it))
                    SingleProgressDialog.hideLoading()
                })


        }
    }

}

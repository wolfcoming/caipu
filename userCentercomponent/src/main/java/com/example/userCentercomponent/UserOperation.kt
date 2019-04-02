package com.example.userCentercomponent

import android.content.Context
import com.example.componentbase.eventbus.UserEvent
import com.infoholdcity.baselibrary.utils.SPUtils
import org.greenrobot.eventbus.EventBus

object UserOperation {

    fun userLoginSuccess(context: Context, it: UserBeanVo) {
        SPUtils.getInstance(context).putObject("userBean", it)
        EventBus.getDefault().post(UserEvent(1))
    }


    fun userLoginOut(context: Context) {
        SPUtils.getInstance(context).putObject("userBean", null)
        EventBus.getDefault().post(UserEvent(2))
    }


}
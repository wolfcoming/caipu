package com.example.userCentercomponent

import android.app.Application
import com.example.componentbase.ServiceFactory
import com.example.userCentercomponent.outservice.UserCenterServiceImpl
import com.infoholdcity.baselibrary.BaseApplaction

class UserCenterApplaction : BaseApplaction() {

    override fun initModuleApp(application: Application) {
        ServiceFactory.instance.setUsercenterService(UserCenterServiceImpl())
    }

    override fun initModuleData(application: Application) {
    }

    override fun onCreate() {
        super.onCreate()
        initModuleApp(this)
        initModuleData(this)
    }
}
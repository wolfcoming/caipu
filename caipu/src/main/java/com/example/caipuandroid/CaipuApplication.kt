package com.example.caipuandroid

import android.app.Application
import com.example.caipuandroid.outService.CaipuOutService
import com.example.componentbase.ServiceFactory
import com.infoholdcity.baselibrary.BaseApplaction

class CaipuApplication : BaseApplaction() {

    override fun initModuleApp(application: Application) {
        ServiceFactory.instance.setCaipuService(CaipuOutService())
    }

    override fun initModuleData(application: Application) {
    }

    override fun onCreate() {
        super.onCreate()
        initModuleApp(this)
        initModuleData(this)
    }
}
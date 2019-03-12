package com.example.shopingmodule

import android.app.Application
import com.infoholdcity.baselibrary.BaseApplaction

class ShopApplaction:BaseApplaction() {
    override fun initModuleApp(application: Application) {

    }

    override fun initModuleData(application: Application) {
    }

    override fun onCreate() {
        super.onCreate()
        initModuleApp(this)
        initModuleData(this)
    }
}
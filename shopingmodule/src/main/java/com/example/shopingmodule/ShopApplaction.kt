package com.example.shopingmodule

import android.app.Application
import com.example.componentbase.ServiceFactory
import com.example.shopingmodule.outservice.ShopOutServiceImpl
import com.example.shopingmodule.ui.Frgm_ShopMain
import com.infoholdcity.baselibrary.BaseApplaction

class ShopApplaction : BaseApplaction() {

    override fun initModuleApp(application: Application) {
        ServiceFactory.instance.setShopService(ShopOutServiceImpl())
    }

    override fun initModuleData(application: Application) {
    }

    override fun onCreate() {
        super.onCreate()
        initModuleApp(this)
        initModuleData(this)
    }
}
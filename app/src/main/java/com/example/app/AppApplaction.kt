package com.example.app

import android.app.Application
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.baselibrary.BaseApplaction
import com.infoholdcity.baselibrary.config.AppConfig
import java.lang.Exception

class AppApplaction : BaseApplaction() {
    override fun initModuleData(application: Application) {
//        反射所有父类
        AppConfig.moduleApps.forEach { cls ->
            try {
                val clazz = Class.forName(cls) as Class
                val baseApp: BaseApplaction = clazz.newInstance() as BaseApplaction
                baseApp.initModuleData(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun initModuleApp(application: Application) {
        AppConfig.moduleApps.forEach { cls ->
            try {
                val clazz = Class.forName(cls) as Class
                val baseApp: BaseApplaction = clazz.newInstance() as BaseApplaction
                baseApp.initModuleApp(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }



}
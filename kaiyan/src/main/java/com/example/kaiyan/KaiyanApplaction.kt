package com.example.kaiyan

import android.app.Application
import com.infoholdcity.baselibrary.BaseApplaction

class KaiyanApplaction:BaseApplaction() {
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
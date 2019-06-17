package com.example.learncomponent

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit
import com.infoholdcity.baselibrary.BaseApplaction

class LearnApplaction :BaseApplaction() {
    override fun onCreate() {
        super.onCreate()
        DoraemonKit.install(this)
    }
    override fun initModuleApp(application: Application) {

    }

    override fun initModuleData(application: Application) {
    }
}
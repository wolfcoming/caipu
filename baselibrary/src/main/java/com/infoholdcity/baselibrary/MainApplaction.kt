package com.infoholdcity.baselibrary

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.didichuxing.doraemonkit.DoraemonKit
import com.squareup.leakcanary.LeakCanary

class MainApplaction : Application() {
    companion object {
        private var mContext: Context? = null
        fun getContext(): Context? {
            return mContext
        }

        fun setContext(context: Context) {
            mContext = context
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        initDoraemon()//调试工具

    }

    private fun initDoraemon() {
        DoraemonKit.install(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
package com.infoholdcity.baselibrary

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.squareup.leakcanary.LeakCanary

open class MainApplaction : Application() {
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
        initArouter()

    }

    private fun initDoraemon() {
        DoraemonKit.install(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    fun initArouter() {
        if (BuildConfig.DEBUG) {
            Klog.e(contents = "ARouter debug模式")// These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
    }
}
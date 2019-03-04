package com.example.caipuandroid

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.squareup.leakcanary.LeakCanary

class MainApplaction : Application() {
    companion object {
        private var mContext: Context? = null
        fun getContext(): Context? {
            return mContext
        }

        fun setContext(context: Context) {
            this.mContext = context
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        LeakCanary.install(this);
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
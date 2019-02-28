package com.example.caipuandroid

import android.app.Application
import android.content.Context

class MainApplaction : Application() {
    companion object {
        private var mContext: Context? = null
        fun getContext(): Context? {
            return mContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}
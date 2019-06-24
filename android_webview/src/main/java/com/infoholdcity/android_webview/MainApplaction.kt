package com.infoholdcity.android_webview

import android.app.Application
import android.util.Log
import com.tencent.smtt.sdk.QbSdk
import com.uuzuche.lib_zxing.activity.ZXingLibrary

/**
 * Author：yangqing
 * Time：2018/12/4 17:33
 * Description：
 */
class MainApplaction:Application() {
    override fun onCreate() {
        super.onCreate()
        initX5webView()
        ZXingLibrary.initDisplayOpinion(this);
    }

    private fun initX5webView() {
//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {

            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}
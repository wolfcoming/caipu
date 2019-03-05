package com.infoholdcity.baselibrary.net.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Author：sun
 * Time：2018/10/9 14:56
 * Description：
 */
object NetWorkHelper {
    fun isNetConnected(context: Context):Boolean{

        val systemService: ConnectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = systemService.getActiveNetworkInfo()

        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected
    }
}
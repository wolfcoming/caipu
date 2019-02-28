package com.hazz.kotlinmvp.net.exception

import com.google.gson.JsonParseException
import com.infoholdcity.basearchitecture.self_extends.Klog

import org.json.JSONException

import java.net.ConnectException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

object ExceptionHandle {


    var errorCode = ErrorStatus.UNKNOWN_ERROR
    var errorMsg = "请求失败，请稍后重试"

    fun handleException(e: Throwable): String {
        e.printStackTrace()
        if (e is SocketTimeoutException) {//网络超时
            Klog.e("TAG", "网络连接异常: " + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e is ConnectException) { //均视为网络错误
            Klog.e("TAG", "网络连接异常: " + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {   //均视为解析错误
            Klog.e("TAG", "数据解析异常: " + e.message)
            errorMsg = "数据解析异常"
            errorCode = ErrorStatus.SERVER_ERROR
        } else if (e is UnknownHostException) {
            Klog.e("TAG", "网络连接异常: " + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e is IllegalArgumentException) {
            errorMsg = "参数错误"
            errorCode = ErrorStatus.SERVER_ERROR
        } else {//未知错误
            try {
                Klog.e("TAG", "错误: " + e.message)
            } catch (e1: Exception) {
                Klog.e("TAG", "未知错误Debug调试 ")
            }

            errorMsg = "未知错误，可能抛锚了吧~"
            errorCode = ErrorStatus.UNKNOWN_ERROR
        }
        return errorMsg
    }


}

package com.example.componentbase.service

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

interface IUserCenterService {

    fun isLogin(): Boolean //是否是登录状态
    fun getUserId(): String? //获取用户id
    fun getUserName(): String? //获取用户id
    fun getMinFragment(
        activity: Activity,
        containerId: Int,
        manager: FragmentManager,
        bundle: Bundle,
        tag: String
    ): Fragment?
}
package com.example.componentbase.emptyImpl

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.componentbase.service.IUserCenterService

class UserCenterEmptyServiceImpl : IUserCenterService {
    override fun getMineFragment(bundle: Bundle?, tag: String): Fragment? {
        return null
    }

    override fun getUserName(): String? {
        return "测试"
    }

    override fun loadFragment(
        activity: Activity,
        containerId: Int,
        manager: FragmentManager,
        bundle: Bundle?,
        tag: String
    ): Fragment? {
        return null
    }

    override fun isLogin(): Boolean {
        return true
    }

    override fun getUserId(): String? {
        return "1111"
    }
}
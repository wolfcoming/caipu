package com.example.userCentercomponent.outservice

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.componentbase.service.IUserCenterService
import com.example.userCentercomponent.MinFragment
import com.example.userCentercomponent.UserBeanVo
import com.infoholdcity.baselibrary.BaseApplaction
import com.infoholdcity.baselibrary.utils.SPUtils
import kotlin.math.min

class UserCenterServiceImpl : IUserCenterService {
    override fun getMineFragment(bundle: Bundle?, tag: String): Fragment {
        val minFragment = MinFragment.Instance(bundle)
        return minFragment
    }


    override fun loadFragment(
        activity: Activity,
        containerId: Int,
        manager: FragmentManager,
        bundle: Bundle?,
        tag: String
    ): Fragment {
        val transaction: FragmentTransaction = manager.beginTransaction()
        val minFragment = MinFragment.Instance(bundle)
        transaction.add(containerId, minFragment, tag)
        transaction.commit()
        return minFragment
    }

    override fun isLogin(): Boolean {
        return SPUtils.getInstance(BaseApplaction.getContext()).getObject<UserBeanVo>("userBean") != null
    }

    override fun getUserId(): String? {
        return SPUtils.getInstance(BaseApplaction.getContext()).getObject<UserBeanVo>("userBean")?.userId
    }

    override fun getUserName(): String? {
        return SPUtils.getInstance(BaseApplaction.getContext()).getObject<UserBeanVo>("userBean")?.name
    }


}
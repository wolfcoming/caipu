package com.example.userCentercomponent.outservice

import com.example.componentbase.service.IUserCenterService
import com.example.userCentercomponent.UserBeanVo
import com.infoholdcity.baselibrary.BaseApplaction
import com.infoholdcity.baselibrary.utils.SPUtils

class UserCenterServiceImpl : IUserCenterService {
    override fun isLogin(): Boolean {
        return SPUtils.getInstance(BaseApplaction.getContext()).getObject<UserBeanVo>("userBean") != null
    }

    override fun getUserId(): String? {
        return SPUtils.getInstance(BaseApplaction.getContext()).getObject<UserBeanVo>("userBean")?.userId
    }
}
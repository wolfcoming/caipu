package com.example.componentbase.emptyImpl

import com.example.componentbase.service.IUserCenterService

class UserCenterEmptyServiceImpl : IUserCenterService {
    override fun isLogin(): Boolean {
        return false
    }

    override fun getUserId(): String? {
        return null
    }
}
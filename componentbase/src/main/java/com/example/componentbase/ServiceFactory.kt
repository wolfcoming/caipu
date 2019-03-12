package com.example.componentbase

import com.example.componentbase.emptyImpl.UserCenterEmptyServiceImpl
import com.example.componentbase.service.IUserCenterService

class ServiceFactory private constructor() {
    companion object {
        val instance: ServiceFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ServiceFactory()
        }
    }

    private var usercenterService: IUserCenterService = UserCenterEmptyServiceImpl()


    fun getUsercenterService(): IUserCenterService {
        return usercenterService
    }

    fun setUsercenterService(usercenter: IUserCenterService) {
        this.usercenterService = usercenter
    }

}
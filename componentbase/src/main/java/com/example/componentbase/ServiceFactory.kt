package com.example.componentbase

import com.example.componentbase.emptyImpl.ICaipuEmptyServiceImpl
import com.example.componentbase.emptyImpl.UserCenterEmptyServiceImpl
import com.example.componentbase.service.ICaipuService
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


    private var caipuService: ICaipuService = ICaipuEmptyServiceImpl()

    fun getCaipuService(): ICaipuService {
        return caipuService
    }

    fun setCaipuService(caipu: ICaipuService) {
        this.caipuService = caipu
    }


}
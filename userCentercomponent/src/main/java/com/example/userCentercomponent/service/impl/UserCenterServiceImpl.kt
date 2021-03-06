package com.example.userCentercomponent.service.impl

import com.example.userCentercomponent.UserBeanVo
import com.example.userCentercomponent.remote.UserApi
import com.example.userCentercomponent.remote.bean.BaseBean
import com.example.userCentercomponent.remote.bean.UserBean
import com.example.userCentercomponent.service.UserCenterService
import com.infoholdcity.baselibrary.BaseApplaction
import com.infoholdcity.baselibrary.net.APIManage
import com.infoholdcity.baselibrary.net.util.ApiException
import com.infoholdcity.baselibrary.utils.SPUtils
import io.reactivex.Observable

class UserCenterServiceImpl : UserCenterService {
    override fun register(username: String, pwd: String): Observable<Boolean> {
        return APIManage.instance.getRequest(UserApi::class.java).register(username, pwd)
            .map {
                dealData(it)
            }
    }

    override fun login(username: String, pwd: String): Observable<UserBeanVo> {
        return APIManage.instance.getRequest(UserApi::class.java).login(username, pwd)
            .map {
                var result: UserBeanVo? = null
                if (dealData(it)) {
                    result = UserBeanVo()
                    val data = it.data
                    data.let {
                        result.userId = it!!.id
                        result.name = it!!.name
                        result.headimg = it!!.name
                        result.is_vip = it!!.is_vip
                        result.usertype = it!!.usertype
                    }
                }
                result
            }
    }


    override fun uploadHeadImg(): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * 统一处理错误
     */
    fun dealData(data: BaseBean<*>, defaulMsg: String = "接口返回错误"): Boolean {
        if (data.code == 1) {
            return true
        } else {
            var msg = ""
            if (data.message == null) {
                msg = defaulMsg
            } else {
                msg = data.message!!
            }
            throw ApiException(msg)
        }
    }
}
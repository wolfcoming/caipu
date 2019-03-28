package com.example.userCentercomponent.mp.contract

import com.example.caipuandroid.base.BasePresenter
import com.example.userCentercomponent.service.UserCenterService
import com.example.userCentercomponent.service.impl.UserCenterServiceImpl
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.baselibrary.utils.MD5Utils

class RegisterPresenter : BasePresenter<RegisterContract.RegisterView>(), RegisterContract.RegisterPresenter {

    val service by lazy { UserCenterServiceImpl() }

    override fun register() {
        val userName = mRootView!!.getUserName()
        val userPwd = mRootView!!.getUserPwd()
        val userPwdConfirm = mRootView!!.getPwdConfirm()
        val mD5Str = MD5Utils.getMD5Str(userPwd)


        mRootView!!.showLoading()
        service.register(userName, mD5Str)
            .excute()
            .subscribe({
                if (it) {
                    mRootView!!.registerSuccess()
                } else {
                    mRootView!!.onError("注册失败")
                }
            }, {
                mRootView!!.onError(it.message!!)
            })
    }


}
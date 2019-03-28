package com.example.userCentercomponent.mp.contract

import com.example.caipuandroid.base.IBaseView
import com.example.caipuandroid.base.IPresenter

interface RegisterContract {
    interface RegisterView : IBaseView {
        fun getUserName(): String
        fun getUserPwd(): String
        fun getPwdConfirm(): String
        fun registerSuccess()
    }


    interface RegisterPresenter : IPresenter<RegisterView> {
        fun register()
    }
}
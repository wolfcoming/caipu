package com.example.componentbase.service

interface IUserCenterService {

    fun isLogin(): Boolean //是否是登录状态
    fun getUserId(): String? //获取用户id

}
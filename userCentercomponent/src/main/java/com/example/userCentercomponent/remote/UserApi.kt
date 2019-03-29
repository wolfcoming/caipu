package com.example.userCentercomponent.remote

import com.example.userCentercomponent.remote.bean.BaseBean
import com.example.userCentercomponent.remote.bean.UserBean
import com.infoholdcity.baselibrary.annotation.BaseUrl
import com.infoholdcity.baselibrary.annotation.TimeOut
import io.reactivex.Observable
import retrofit2.http.*

@BaseUrl(URLConfig.userBaseurl)
@TimeOut(value = 5)
interface UserApi {

    /**
     * 注册
     */

    @POST("/register/")
    @FormUrlEncoded
    fun register(
        @Field("name") name: String,
        @Field("pwd") pwd: String
    ): Observable<BaseBean<UserBean>>




    /**
     * 登录
     */

    @POST("/login/")
    @FormUrlEncoded
    fun login(
        @Field("name") name: String,
        @Field("pwd") pwd: String
    ): Observable<BaseBean<UserBean>>


}
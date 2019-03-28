package com.example.userCentercomponent.remote

import com.example.userCentercomponent.remote.bean.BaseBean
import com.example.userCentercomponent.remote.bean.UserBean
import com.infoholdcity.baselibrary.annotation.BaseUrl
import com.infoholdcity.baselibrary.annotation.TimeOut
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

@BaseUrl(URLConfig.userBaseurl)
@TimeOut(value = 15)
interface UserApi {
    /**
     * 注册
     */
    @GET("/register/")
    fun register(
        @Query("name") name: String,
        @Query("pwd") pwd: String
    ): Observable<BaseBean<UserBean>>

}
package com.example.caipuandroid.remote

import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.remote.bean.CategoryBean
import com.infoholdcity.baselibrary.annotation.BaseUrl
import com.infoholdcity.baselibrary.annotation.TimeOut
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


@BaseUrl(URLConfig.caipuBaseurl)
@TimeOut(value = 15)
interface APIService {

    /**
     * 首页精选
     */
    @GET("/getMenu")
    fun getCategorys(): Observable<BaseBean<List<CategoryBean>>>

}
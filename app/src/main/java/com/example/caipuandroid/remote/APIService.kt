package com.example.caipuandroid.remote

import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.remote.bean.CategoryBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    /**
     * 首页精选
     */
    @GET("/getMenu")
    fun getCategorys(): Observable<BaseBean<List<CategoryBean>>>

}
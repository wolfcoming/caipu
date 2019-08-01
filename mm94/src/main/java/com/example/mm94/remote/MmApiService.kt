package com.example.caipuandroid.remote

import com.example.mm94.remote.bean.BaseBean
import com.example.mm94.remote.bean.MmBean
import com.infoholdcity.baselibrary.annotation.BaseUrl
import com.infoholdcity.baselibrary.annotation.TimeOut
import io.reactivex.Observable
import retrofit2.http.*


@BaseUrl(URLConfig.mm94Baseurl)
@TimeOut(value = 15)
interface MmApiService {

    @GET("/getMmList/")
    fun getMmList(): Observable<BaseBean<List<MmBean>>>

}
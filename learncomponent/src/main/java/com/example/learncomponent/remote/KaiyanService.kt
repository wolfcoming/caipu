package com.example.kaiyan.remote

import com.example.learncomponent.remote.GuanzhuListBean
import com.infoholdcity.baselibrary.annotation.BaseUrl
import io.reactivex.Observable
import retrofit2.http.GET

@BaseUrl(KaiyanUrlConfig.baseUrl)
interface KaiyanService {

    @GET("api/v6/community/tab/follow?udid=155a5b5a923745e3a47b3c6f3950c398855da144&vc=481&vn=5.3&size=1080X1920&deviceModel=Mi%20Note%203&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=27")
    fun categoryList(): Observable<GuanzhuListBean>

}
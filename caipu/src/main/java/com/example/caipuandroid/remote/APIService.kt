package com.example.caipuandroid.remote

import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.remote.bean.CategoryBean
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.baselibrary.annotation.BaseUrl
import com.infoholdcity.baselibrary.annotation.TimeOut
import io.reactivex.Observable
import retrofit2.http.*


@BaseUrl(URLConfig.caipuBaseurl)
@TimeOut(value = 15)
interface APIService {

    /**
     * 首页精选
     */
    @GET("/getMenu/")
    fun getCategorys(): Observable<BaseBean<List<CategoryBean>>>


    @POST("/addCaipu/")
    fun addGreens(@Body greens: Greens): Observable<BaseBean<String>>


    @GET("/qntoken/")
    fun getQiNiuToken(): Observable<BaseBean<String>>


    @GET("/getGreensList/")
    fun getCaipuList(@Query("pageSize") pageSize:Int,
                     @Query("pageNumber") pageNumber:Int):Observable<BaseBean<List<Greens>>>

}
package com.example.shopingmodule.remote

import com.example.shopingmodule.remote.bean.Bannerbean
import com.example.shopingmodule.remote.bean.BaseBean
import com.example.shopingmodule.remote.bean.ShopCategoryBean
import com.infoholdcity.baselibrary.annotation.BaseUrl
import io.reactivex.Observable
import retrofit2.http.GET

@BaseUrl(ShopUrlConfig.baseUrl)
interface ShopNetService {

    @GET("category/out/children?key=0db6ffd00372064035ef33763dd1c61e&t=1547700576328")
    fun categoryList(): Observable<BaseBean<List<ShopCategoryBean>>>
    @GET("shopping/banner/list?key=0db6ffd00372064035ef33763dd1c61e&t=1547700576328")
    fun bannerList(): Observable<BaseBean<Bannerbean>>
}
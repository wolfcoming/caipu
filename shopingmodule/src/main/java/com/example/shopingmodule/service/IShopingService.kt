package com.example.shopingmodule.service

import com.example.shopingmodule.ui.vo.SelectionVo
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import io.reactivex.Observable

interface IShopingService {
    //    分类
    fun getShopCategory(): Observable<List<ShopCategoryVo>>

    //首页商品
    fun getBanner(): Observable<List<ShopBannerVo>>

    //精选
    fun getSelect(page:String): Observable<List<SelectionVo>>

}
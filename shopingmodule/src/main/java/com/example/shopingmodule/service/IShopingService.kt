package com.example.shopingmodule.service

import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import io.reactivex.Observable

interface IShopingService {
    fun getShopCategory(): Observable<List<ShopCategoryVo>>

    fun getBanner(): Observable<List<ShopBannerVo>>
}
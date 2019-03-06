package com.example.shopingmodule.service.impl

import com.example.shopingmodule.remote.ShopNetService
import com.example.shopingmodule.service.IShopingService
import com.example.shopingmodule.ui.vo.ProductVo
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import com.infoholdcity.baselibrary.net.APIManage
import com.infoholdcity.baselibrary.net.util.ApiException
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

class ShopingService : IShopingService {
    override fun getBanner(): Observable<List<ShopBannerVo>> {

        return APIManage.instance.getRequest(ShopNetService::class.java).bannerList()
            .map { it ->
                val result: ArrayList<ShopBannerVo> = ArrayList()
                if (it.code == 200) {
                    it.data!!.content.map {
                        val shopBannerVo = ShopBannerVo()
                        shopBannerVo.image = it.image
                        shopBannerVo.name = it.name
                        shopBannerVo.title = it.title
                        val items: ArrayList<ProductVo> = ArrayList()
                        it.items.map { itemsBean ->
                            val productVo = ProductVo()
                            productVo.brandName = itemsBean.brandName
                            productVo.keyword = itemsBean.keyword
                            productVo.price = itemsBean.price
                            productVo.image = itemsBean.image
                            items.add(productVo)
                        }
                        shopBannerVo.items = items
                        result.add(shopBannerVo)
                    }
                    result
                } else {
                    var msg = ""
                    if (it.msg == null) {
                        msg = "接口返回错误"
                    } else {
                        msg = it.msg
                    }
                    throw ApiException(msg)
                }
            }
    }

    override fun getShopCategory(): Observable<List<ShopCategoryVo>> {
        return APIManage.instance.getRequest(ShopNetService::class.java).categoryList()
            .map { it ->
                if (it.code == 200) {
                    val list = it.data
                    val result: ArrayList<ShopCategoryVo> = ArrayList()
                    list!!.map {
                        val shopCategoryVo = ShopCategoryVo(it.logo, it.name, it.id.toString())
                        result.add(shopCategoryVo)
                    }
                    result

                } else {
                    var msg = ""
                    if (it.msg == null) {
                        msg = "接口返回错误"
                    } else {
                        msg = it.msg
                    }
                    throw ApiException(msg)
                }
            }


    }

}
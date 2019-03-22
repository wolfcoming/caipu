package com.example.shopingmodule.service.impl

import com.example.shopingmodule.remote.ShopNetService
import com.example.shopingmodule.remote.bean.BaseBean
import com.example.shopingmodule.service.IShopingService
import com.example.shopingmodule.ui.vo.ProductVo
import com.example.shopingmodule.ui.vo.SelectionVo
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import com.infoholdcity.baselibrary.net.APIManage
import com.infoholdcity.baselibrary.net.util.ApiException
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

class ShopingService : IShopingService {


    override fun getSelect(page:String): Observable<List<SelectionVo>> {
        return APIManage.instance.getRequest(ShopNetService::class.java)
            .selectionList(page)
            .map { it ->
                val result: ArrayList<SelectionVo> = ArrayList()
                if (dealData(it)) {
                    it.data!!.records!!.map { recordsBean ->
                        var selectionVo = SelectionVo()
                        selectionVo.brand = recordsBean.brand
                        selectionVo.favNum = recordsBean.favNum
                        selectionVo.image = recordsBean.image
                        selectionVo.price = recordsBean.price
                        selectionVo.productDescription = recordsBean.productDescription
                        result.add(selectionVo)
                    }
                }
                result
            }

    }
    override fun getBanner(): Observable<List<ShopBannerVo>> {

        return APIManage.instance.getRequest(ShopNetService::class.java).bannerList()
            .map { it ->
                val result: ArrayList<ShopBannerVo> = ArrayList()
                if (dealData(it)) {
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
                }
                result
            }
    }

    override fun getShopCategory(): Observable<List<ShopCategoryVo>> {
        return APIManage.instance.getRequest(ShopNetService::class.java).categoryList()
            .map { it ->
                val result: ArrayList<ShopCategoryVo> = ArrayList()
                if (dealData(it)) {
                    val list = it.data
                    list!!.map {
                        val shopCategoryVo = ShopCategoryVo(it.logo, it.name, it.id.toString())
                        result.add(shopCategoryVo)
                    }
                }
                result
            }
    }



    /**
     * 统一处理错误
     */
    fun dealData(data: BaseBean<*>, defaulMsg: String = "接口返回错误"): Boolean {
        if (data.code == 200) {
            return true
        } else {
            var msg = ""
            if (data.msg == null) {
                msg = defaulMsg
            } else {
                msg = data.msg
            }
            throw ApiException(msg)
        }
    }
}
package com.example.shopingmodule.mvp

import com.example.caipuandroid.base.BasePresenter
import com.example.shopingmodule.service.impl.ShopingService
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import com.infoholdcity.basearchitecture.self_extends.excute
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class HomePresenter : BasePresenter<HomeContract.IHomeView>(), HomeContract.Ipresenter {
    val service by lazy { ShopingService() }
    override fun getData() {
        Observable.zip(service.getShopCategory(), service.getBanner(),
            BiFunction<List<ShopCategoryVo>, List<ShopBannerVo>, HashMap<String, Any>> { shopCategoryVos, shopBannerVos ->
                val data = HashMap<String, Any>()
                data.put("category", shopCategoryVos)
                data.put("bannerlist", shopBannerVos)
                data
            }).excute()
            .subscribe({
                mRootView!!.showData(it)
            },{
                mRootView!!.onError(it.message!!)
            })
    }
}
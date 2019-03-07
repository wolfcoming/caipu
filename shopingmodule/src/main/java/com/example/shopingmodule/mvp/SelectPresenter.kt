package com.example.shopingmodule.mvp

import com.example.caipuandroid.base.BasePresenter
import com.example.shopingmodule.service.impl.ShopingService
import com.infoholdcity.basearchitecture.self_extends.excute

class SelectPresenter : BasePresenter<SelectContract.IView>(), SelectContract.Presnter {
    val service by lazy { ShopingService() }
    override fun getSelection(page: String) {
        service.getSelect(page)
            .excute()
            .subscribe({
                mRootView!!.showSelection(it)
            }, {
                mRootView!!.onError(it.message!!)
            })
    }
}
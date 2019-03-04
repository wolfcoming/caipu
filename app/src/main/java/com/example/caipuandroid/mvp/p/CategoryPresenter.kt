package com.example.caipuandroid.mvp.p

import com.example.caipuandroid.base.BasePresenter
import com.example.caipuandroid.mvp.contract.CategoryContract
import com.example.caipuandroid.mvp.mode.CategoryMode
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.infoholdcity.basearchitecture.self_extends.excute

class CategoryPresenter() : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    val categoryMode: CategoryMode by lazy { CategoryMode() }

    override fun getCategoryData() {
        val subscribe = categoryMode.getCategorys()
            .excute()
            .subscribe({ it ->
                mRootView?.showCategory(it!!)
            }, {
                mRootView?.onError(ExceptionHandle.handleException(it))
            }, {
                mRootView?.hideLoading()
            })

        addCompositeDisposable(subscribe)
    }


}
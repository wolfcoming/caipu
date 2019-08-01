package com.example.caipuandroid.mvp.p

import com.example.caipuandroid.base.BasePresenter
import com.example.caipuandroid.db.AppDatabase
import com.example.caipuandroid.db.CategoryEntity
import com.example.caipuandroid.mvp.contract.CategoryContract
import com.example.caipuandroid.mvp.mode.CategoryMode
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.infoholdcity.basearchitecture.self_extends.excute

class CategoryPresenter() : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    val categoryMode: CategoryMode by lazy { CategoryMode() }

    override fun getCategoryData() {
        mRootView?.showLoading()
        val subscribe = categoryMode.getCategorys()
            .excute()
            .subscribe({ it ->
                mRootView?.showCategory(it!!)
            }, {
                //获取不到则使用本地数据库
                mRootView?.onError(ExceptionHandle.handleException(it))
//                mRootView?.onError(ExceptionHandle.handleException(it))
            }, {
                mRootView?.hideLoading()
            })

        addCompositeDisposable(subscribe)
    }
}
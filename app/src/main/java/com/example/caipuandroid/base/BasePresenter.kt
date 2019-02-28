package com.example.caipuandroid.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mRootView: T? = null


    private var compositeDisposable = CompositeDisposable()

    /**
     * 关联v到p
     */
    override fun attachView(rootview: T) {
        mRootView = rootview
    }

    override fun detachView() {
        mRootView = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }


    private val isViewAttached: Boolean
        get() = mRootView != null


}
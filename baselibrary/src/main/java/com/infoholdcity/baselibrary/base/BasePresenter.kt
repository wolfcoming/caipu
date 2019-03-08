package com.example.caipuandroid.base

import com.infoholdcity.basearchitecture.self_extends.Klog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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
        Klog.e(contents = "detachView 方法执行")
        mRootView = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }


    private val isViewAttached: Boolean
        get() = mRootView != null


    /**
     * 添加容器中 方便统一销毁
     */
    fun addCompositeDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

}
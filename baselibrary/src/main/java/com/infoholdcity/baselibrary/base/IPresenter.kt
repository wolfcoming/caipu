package com.example.caipuandroid.base

interface IPresenter<V : IBaseView> {


    fun attachView(mRootView: V)

    fun detachView()

}

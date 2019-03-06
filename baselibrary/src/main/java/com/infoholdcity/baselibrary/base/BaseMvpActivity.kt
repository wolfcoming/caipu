package com.example.caipuandroid.base

import android.content.Context
import android.os.Bundle
import com.infoholdcity.baselibrary.base.BaseActiviy

abstract class BaseMvpActivity<P : IPresenter<*>> : BaseActiviy(), IBaseView {

    lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getPresenter()
        //TODO 为什么不能再这儿绑定view
//        mPresenter.attachView(this)//关联p和v
        attachView(mPresenter)

    }


    override fun getContext(): Context {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    //定义两个抽象方法，提醒使用者要实现改方法
    abstract fun getPresenter(): P

    abstract fun attachView(presenter: P)

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }

}
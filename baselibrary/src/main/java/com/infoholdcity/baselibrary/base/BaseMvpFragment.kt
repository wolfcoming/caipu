package com.example.caipuandroid.base

import android.content.Context
import android.os.Bundle
import android.view.View
import com.infoholdcity.baselibrary.base.BaseFragment

abstract class BaseMvpFragment<P : IPresenter<*>> : BaseFragment(), IBaseView {

    lateinit var mPresenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter = getPresenter()
        //TODO 为什么不能再这儿绑定view
//        mPresenter.attachView(this)//关联p和v
        attachView(mPresenter)
        super.onViewCreated(view, savedInstanceState)

    }


    override fun getCon(): Context {
        return context!!
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
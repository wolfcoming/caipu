package com.example.caipuandroid.base

import android.content.Context
import android.os.Bundle
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.base.TakePhotoBaseActivity

abstract class BaseMvpActivity<P : IPresenter<*>> : TakePhotoBaseActivity(), IBaseView {

    lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getPresenter()
        attachView(mPresenter)

    }


    override fun getCon(): Context {
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
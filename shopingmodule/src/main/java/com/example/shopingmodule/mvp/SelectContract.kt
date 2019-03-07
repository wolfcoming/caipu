package com.example.shopingmodule.mvp

import com.example.caipuandroid.base.BasePresenter
import com.example.caipuandroid.base.IBaseView
import com.example.caipuandroid.base.IPresenter
import com.example.shopingmodule.ui.vo.SelectionVo

interface SelectContract {
    interface IView : IBaseView {
        fun showSelection(datas: List<SelectionVo>)
    }

    interface Presnter : IPresenter<IView> {
        fun getSelection(page: String)
    }
}
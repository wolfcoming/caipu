package com.example.shopingmodule.mvp

import com.example.caipuandroid.base.BasePresenter
import com.example.caipuandroid.base.IBaseView
import com.example.caipuandroid.base.IPresenter

interface HomeContract {
    interface IHomeView:IBaseView{
        fun showData(datas:HashMap<String,Any>)
    }


    interface Ipresenter: IPresenter<IHomeView>{
        fun getData()
    }

}
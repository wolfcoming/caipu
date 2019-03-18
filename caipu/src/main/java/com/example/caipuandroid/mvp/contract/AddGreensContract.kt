package com.example.caipuandroid.mvp.contract

import com.example.caipuandroid.base.IBaseView
import com.example.caipuandroid.base.IPresenter
import com.example.caipuandroid.ui.vo.CategoryVo
import com.example.caipuandroid.ui.vo.Greens

/**
 * desc: 分类 契约类
 */
interface AddGreensContract {

    interface View : IBaseView {


    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取七牛token
         */
        fun getQiNiuToken()


        /**
         * 添加菜谱
         */
        fun addGreens(greens: Greens)
    }
}
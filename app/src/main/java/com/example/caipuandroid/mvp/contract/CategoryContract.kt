package com.example.caipuandroid.mvp.contract

import com.example.caipuandroid.base.IBaseView
import com.example.caipuandroid.base.IPresenter
import com.example.caipuandroid.ui.vo.CategoryVo

/**
 * desc: 分类 契约类
 */
interface CategoryContract {

    interface View : IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategory(it: List<CategoryVo>)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}
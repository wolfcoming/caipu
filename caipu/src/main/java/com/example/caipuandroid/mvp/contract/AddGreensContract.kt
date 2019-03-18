package com.example.caipuandroid.mvp.contract

import com.example.caipuandroid.base.IBaseView
import com.example.caipuandroid.base.IPresenter
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.CategoryVo
import com.example.caipuandroid.ui.vo.Greens
import com.example.caipuandroid.ui.vo.MakesBean

/**
 * desc: 分类 契约类
 */
interface AddGreensContract {

    interface View : IBaseView {
        fun getName(): String
        fun getTips(): String
        fun getCoverImg():String//获取封面图地址
        fun getBurdens(): ArrayList<BurdenBean>
        fun getMakes(): ArrayList<MakesBean>

        fun addSuccess()
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取七牛token
         */
        fun getQiNiuToken()


        /**
         * 添加菜谱
         */
        fun addGreens()
    }
}
package com.example.caipuandroid.mvp.mode

import com.example.caipuandroid.service.ICaipuService
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.vo.CategoryVo
import com.google.gson.Gson
import io.reactivex.Observable

/**
 * model 层 主要功能是获取数据，数据可能来自网络，缓存数据库
 */
class CategoryMode {
    private val service: ICaipuService by lazy { IServiceNetImpl() }
    fun getCategorys(): Observable<List<CategoryVo>> {
        val categorys = service.getCategorys()
        return categorys
    }
}
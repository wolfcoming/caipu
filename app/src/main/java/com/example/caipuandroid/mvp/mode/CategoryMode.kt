package com.example.caipuandroid.mvp.mode

import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.service.IService
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.vo.CategoryVo
import io.reactivex.Observable

/**
 * model 层 主要功能是获取数据，数据可能来自网络，缓存数据库
 */
class CategoryMode {
    private val service: IService by lazy { IServiceNetImpl() }
    fun getCategorys(): Observable<BaseBean<List<CategoryVo>>> {
        return service.getCategorys()
    }
}
package com.example.caipuandroid.service

import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.ui.vo.CategoryVo
import io.reactivex.Observable

interface IService {
    /**
     * 获取菜单分类
     */
    fun getCategorys(): Observable<BaseBean<List<CategoryVo>>>
}
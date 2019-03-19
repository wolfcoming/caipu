package com.example.caipuandroid.service

import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.ui.vo.CategoryVo
import com.example.caipuandroid.ui.vo.Greens
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.PartMap

interface ICaipuService {

    /**
     * 获取菜单分类
     */
    fun getCategorys(): Observable<List<CategoryVo>>

    /**
     * 添加菜的做法
     */
    fun addGreens( greens: Greens):Observable<Boolean>


    /**
     * 获取七牛token
     */
    fun getQiNiuToken():Observable<String>


    /**
     * 获取菜谱列表
     */
    fun getGreensList(pageSize:Int,pageNumber:Int):Observable<List<Greens>>




}
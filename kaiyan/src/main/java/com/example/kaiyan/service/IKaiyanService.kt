package com.example.kaiyan.service

import com.example.kaiyan.remote.GuanzhuListBean
import com.example.kaiyan.remote.GuanzhuListBean.ItemListBean.DataBeanX.ContentBean
import io.reactivex.Observable

interface IKaiyanService {
    fun getGuanzhuList(): Observable<List<ContentBean.ItemBean>>
}
package com.example.kaiyan.service

import com.example.kaiyan.remote.GuanzhuListBean
import com.example.kaiyan.remote.GuanzhuListBean.ItemListBean.DataBeanX.ContentBean.ItemBean
import com.example.kaiyan.remote.KaiyanService
import com.infoholdcity.baselibrary.net.APIManage
import io.reactivex.Observable

class KaiyanServiceImpl : IKaiyanService {
    override fun getGuanzhuList(): Observable<List<ItemBean>> {
        return APIManage.instance.getRequest(KaiyanService::class.java).categoryList()
            .map {
//                it.itemList
                val list:ArrayList<ItemBean> = ArrayList()
                it.itemList.map {
                    list.add(it.data.content.data)
                }
                list

            }

    }
}
package com.example.mm94.service.impl

import com.example.caipuandroid.remote.MmApiService
import com.example.mm94.service.IMmService
import com.example.mm94.ui.vo.MmBeanVo
import com.infoholdcity.baselibrary.net.APIManage
import com.infoholdcity.baselibrary.net.util.ApiException
import io.reactivex.Observable

class MmService : IMmService {
    override fun getMmList(): Observable<List<MmBeanVo>> {
        return APIManage.instance.getRequest(MmApiService::class.java)
            .getMmList()
            .map {
                val list: ArrayList<MmBeanVo> = ArrayList()
                if (it.code == 1) {
                    it.data!!.forEach { mmBean ->
                        val beanVo = MmBeanVo()
                        beanVo.title = mmBean.title
                        beanVo.thumimg = mmBean.thumimg
                        beanVo.url = mmBean.url


                        val split = mmBean.images.split(",")
                        val imgList = ArrayList<String>()
                        split.forEach { image ->
                            if (!image.isEmpty()) {
                                imgList.add(image)
                            }
                        }
                        beanVo.images = imgList
                        list.add(beanVo)
                    }
                } else {
                    throw ApiException("${it.message}")
                }
                list
            }
    }
}
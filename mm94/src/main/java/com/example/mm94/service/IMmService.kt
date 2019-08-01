package com.example.mm94.service

import com.example.mm94.ui.vo.MmBeanVo
import io.reactivex.Observable

interface IMmService {

    /**
     * 获取mm列表
     */
    fun getMmList(): Observable<List<MmBeanVo>>
}
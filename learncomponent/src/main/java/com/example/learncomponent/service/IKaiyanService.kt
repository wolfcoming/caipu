package com.example.kaiyan.service

import com.example.learncomponent.remote.ItemBean
import io.reactivex.Observable

interface IKaiyanService {
    fun getGuanzhuList(): Observable<List<ItemBean>>
}
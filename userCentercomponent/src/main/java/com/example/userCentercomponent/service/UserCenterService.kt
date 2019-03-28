package com.example.userCentercomponent.service

import com.example.userCentercomponent.UserBeanVo
import io.reactivex.Observable

interface UserCenterService {

    fun register(username: String, pwd: String): Observable<Boolean>

    fun login(username: String, pwd: String): Observable<UserBeanVo>

    fun uploadHeadImg(): Observable<String>


}
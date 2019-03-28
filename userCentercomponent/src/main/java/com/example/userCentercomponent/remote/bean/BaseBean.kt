package com.example.userCentercomponent.remote.bean

import java.io.Serializable

class BaseBean<T> : Serializable {
    var code: Int = 0
    var data: T? = null
    var message: String? = null
}
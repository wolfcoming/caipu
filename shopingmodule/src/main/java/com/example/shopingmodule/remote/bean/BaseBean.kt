package com.example.shopingmodule.remote.bean

class BaseBean<T> {
    val code: Int = 0
    val msg: String? = null
    val success: Boolean? = null
    val data: T? = null
}
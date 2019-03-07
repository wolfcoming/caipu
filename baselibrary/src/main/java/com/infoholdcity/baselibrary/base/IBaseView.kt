package com.example.caipuandroid.base

import android.content.Context


open interface IBaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(message: String)
    fun getCon(): Context
}
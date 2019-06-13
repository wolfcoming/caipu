package com.infoholdcity.basearchitecture.self_extends

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *@date     创建时间： 2018/9/21
 *@author   创建人：杨庆
 *@descript 描述：
 *@version
 */
fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun ViewGroup.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun <T> Observable<T>.excute(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}

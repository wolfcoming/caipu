package com.infoholdcity.baselibrary.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus

/**
 *@date     创建时间： 2018/11/2
 *@author   创建人：杨庆
 *@descript 描述：
 *@version
*/
abstract class BaseFragment :Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflateView = inflateView()
        var view: View?
        if (inflateView is Int) {
            view = inflater.inflate(Integer.parseInt(inflateView.toString()), container, false)
        } else if (inflateView is View) {
            view = inflateView
        } else {
            throw RuntimeException("unexpected view inflate :$inflateView")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    protected abstract fun inflateView(): Any

    protected abstract fun initView(anchor: View)
}
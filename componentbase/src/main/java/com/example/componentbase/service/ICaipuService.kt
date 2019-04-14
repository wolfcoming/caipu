package com.example.componentbase.service

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

interface ICaipuService {


    //获取菜谱分类fragment
    fun getCategoryFragment( bundle: Bundle?,
                             tag: String):Fragment

    //获取菜谱首页界面
    fun getCaipuHomeFragment(bundle: Bundle?,tag: String):Fragment
}
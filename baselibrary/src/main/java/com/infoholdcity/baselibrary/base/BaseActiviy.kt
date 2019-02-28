package com.infoholdcity.baselibrary.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.noober.background.BackgroundLibrary
import com.yq.wolfcoming.baselibrary.common.AppManage

/**
 * Author：yangqing
 * Time：2018/12/11 16:46
 * Description：
 */
abstract class BaseActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        //   解决共享元素闪屏问题
        getWindow().setBackgroundDrawableResource(android.R.color.transparent)
        AppManage.getSingleInstance().addActivity(this)
    }

    override fun onDestroy() {
        AppManage.getSingleInstance().removeActivity(this)
        super.onDestroy()
    }


    fun changeStateBarColor(color: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color)
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
}
package com.yq.wolfcoming.baselibrary.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * Created by yangqing on 2018/6/7.
 */
class AppManage private constructor() {

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        @Volatile
        private var Instance: AppManage? = null

        fun getSingleInstance(): AppManage {
            if (Instance == null) {
                synchronized(AppManage::class.java) {
                    if (Instance == null) {
                        Instance = AppManage()
                    }
                }
            }
            return Instance!!
        }
    }


    /**
     * 入栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    /**
     * 获取当前栈顶
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }


    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()

    }

    @SuppressLint("MissingPermission")
            /**
             * 退出应用
             */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManage =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        activityManage.killBackgroundProcesses(context.packageName)
        System.exit(0)

    }


}
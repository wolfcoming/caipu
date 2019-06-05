package com.example.learncomponent.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.example.learncomponent.R
import com.infoholdcity.basearchitecture.self_extends.Klog

class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {

        return null
    }


    override fun onCreate() {
        super.onCreate()
        Klog.e(contents = "onCreate")


    }

    /**
     * 创建通知，让服务变成前台服务 提升存活优先级
     */
    private fun createNotify() {
        //创建通知通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("111", "渠道1", NotificationManager.IMPORTANCE_HIGH)
            createNotificationChannel("222", "渠道2", NotificationManager.IMPORTANCE_HIGH)
        }


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "111")
            .setContentTitle("前台进程")
            .setContentText("弹出一个通知让服务变成前台进程")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setAutoCancel(false)
            .build()
        notificationManager.notify(1, notification)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Klog.e(contents = "onStartCommand")
        createNotify()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Klog.e(contents = "onDestroy")
        super.onDestroy()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(channelId: String, channelName: String, importance: Int) {
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}
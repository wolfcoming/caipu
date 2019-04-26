package com.example.userCentercomponent.test

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import com.infoholdcity.basearchitecture.self_extends.Klog
import org.json.JSONObject

class AIDLService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return stub
    }

    internal var stub: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub() {
        @Throws(RemoteException::class)
        override fun getData(func: String, params: String):String {
            Klog.e(contents = "接收到消息")
            Klog.e(contents = "func:${func};params:${params}")

            val jsonObject = JSONObject()
            when(func){
                "char"->{
                    jsonObject.put("name","yangqing")
                    jsonObject.put("sex","man")
                    val mills = System.currentTimeMillis()
                    jsonObject.put("time",mills)

                }

            }

            val s = jsonObject.toString()
            return s

        }
    }

}
package com.example.learncomponent

import android.graphics.BitmapFactory
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.provider.MediaStore.Video.VideoColumns.LANGUAGE
import android.util.Log
import android.widget.Toast
import com.example.learncomponent.proxy.Subject
import com.example.learncomponent.proxy.WoMen
import com.example.learncomponent.proxy.WoMenProxy
import com.googlecode.tesseract.android.TessBaseAPI
import com.googlecode.tesseract.android.TessBaseAPI.OEM_CUBE_ONLY
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.TakePhotoBaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.devio.takephoto.model.TResult
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.reflect.Proxy

class MainActivity : TakePhotoBaseActivity() {

    var threadHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = NDKTools.getStringFromNDK()
        tvContent.text = text
        val mainHandler = MainHanlder()
        btnTest.setOnClickListener {
            Thread(Runnable {
//                // 子线程发送handler 给主线程
//                mainHandler.sendEmptyMessage(1)
//                Looper.prepare()
//                threadHandler = object : Handler() {
//                    override fun handleMessage(msg: Message) {
//                        super.handleMessage(msg)
//                        Klog.e(contents = "what 主线程 向我发送了一条信息！！！")
//
//                    }
//                }
//                Looper.loop()
//                Klog.e(contents = "线程到这儿就死了。。")


                val handler = object :Handler(Looper.getMainLooper()){
                    override fun handleMessage(msg: Message?) {
                        super.handleMessage(msg)
                       toast("shit")
                    }
                }

                handler.sendEmptyMessage(1)
            }).start()
        }
    }

    inner class MainHanlder : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                Klog.e(contents = "1111")
                threadHandler?.sendEmptyMessage(2)
            }
        }
    }
}

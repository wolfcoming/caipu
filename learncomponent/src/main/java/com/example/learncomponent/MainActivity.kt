package com.example.learncomponent

import android.content.Context
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
import com.infoholdcity.baselibrary.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.devio.takephoto.model.TResult
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.reflect.Proxy
import android.content.Context.WINDOW_SERVICE
import android.content.res.Configuration
import android.graphics.Color
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.learncomponent.fresh.RefreshCallback
import com.example.learncomponent.fresh.SimpleRefreshLayout
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var threadHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.layoutManager = LinearLayoutManager(this)
        val myAdapter = SimpleAdapter()
        rv.adapter = myAdapter

        for (i in 0..40) {
            myAdapter.addData(i.toString())
        }
        myAdapter.notifyDataSetChanged()

        refreshLayout.setRefreshCallbackListener { refreshLayout ->
            Handler().postDelayed({

                val datas = ArrayList<String>()
                for (i in 0..30) {
                    datas.add("当前元素" + i)
                }
                myAdapter.setNewData(datas)
                myAdapter.notifyDataSetChanged()
                refreshLayout?.freshFinished()
            }, 2000)
        }

        refreshLayout.setLoadCallbackListener { refreshLayout ->
            Handler().postDelayed({
                val datas = ArrayList<String>()
                for (i in 0..5) {
                    datas.add("新加元素" + i)
                }
                myAdapter.addData(datas)
                myAdapter.notifyDataSetChanged()
                refreshLayout?.loadFinished()
            }, 1000)

        }

    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toast("onConfigurationChanged")
    }

}

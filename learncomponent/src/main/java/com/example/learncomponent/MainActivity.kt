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

        for(i in 0..40){
            myAdapter.addData(i.toString())
        }
        myAdapter.notifyDataSetChanged()



    }

//    private fun initview() {
//
//
//        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val dm = DisplayMetrics()
//        wm.defaultDisplay.getMetrics(dm)
//        val width = dm.widthPixels         // 屏幕宽度（像素）
//        val height = dm.heightPixels       // 屏幕高度（像素）
//        val colors = Arrays.asList("#f00", "#0f0", "#00f")
//
//        for (i in 0 until 3) {
//            val layout = LayoutInflater.from(this).inflate(R.layout.hs_content, hscontainer, false)
//            layout.layoutParams.width = width
//            val tvPage = layout.findViewById<TextView>(R.id.page)
//            tvPage.text = "page${i}"
////            layout.setBackgroundColor(Color.parseColor(colors[i]))
//            createListView(layout)
//            hscontainer.addView(layout)
//        }
//    }
//
//    private fun createListView(layout: View) {
//        val listView = layout.findViewById<ListView>(R.id.listview)
//        val datas = ArrayList<String>()
//        for (i in 0..50) {
//            datas.add("item: " + i.toString())
//        }
//        val adapter = ArrayAdapter<String>(this, R.layout.content_list_item, R.id.name, datas)
//        listView.adapter = adapter
//    }


}

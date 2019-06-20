package com.example.learncomponent.activity

import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.learncomponent.R
import com.example.learncomponent.adapter.SimpleAdapter
import com.example.learncomponent.fresh.RefreshActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        val simpleAdapter = SimpleAdapter()
        rvMain.adapter = simpleAdapter
        var datas = LinkedHashMap<String, Class<*>>()
        datas.put("刷新控件", RefreshActivity::class.java)
        datas.put("滑动嵌套_悬停布局", NestActivity::class.java)
        datas.put("仿微博视频列表拖动播放", VideoListAutoPlayActivity::class.java)
        datas.put("自定义LayoutManage", LayoutmanageActivity::class.java)
        datas.put("Android Q 适配 未做", ShipeiActivity::class.java)


        val lists = ArrayList<String>()
        for (data in datas) {
            lists.add(data.key)
        }

        simpleAdapter.setNewData(lists)

        simpleAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(this, datas[lists[position]]))
        }

    }


}

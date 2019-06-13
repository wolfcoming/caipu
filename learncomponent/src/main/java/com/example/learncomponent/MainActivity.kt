package com.example.learncomponent

import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.learncomponent.adapter.SimpleAdapter
import com.example.learncomponent.adapter.SimpleAdapter2
import com.example.learncomponent.fresh.RefreshActivity
import com.infoholdcity.baselibrary.view.freshview.LoadMoreWrapperAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        val simpleAdapter = SimpleAdapter()
        rvMain.adapter = simpleAdapter
        var datas = HashMap<String, Class<*>>()
        datas.put("刷新控件", RefreshActivity::class.java)
        datas.put("滑动嵌套_悬停布局", NestActivity::class.java)


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

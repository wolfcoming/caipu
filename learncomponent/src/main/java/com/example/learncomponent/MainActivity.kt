package com.example.learncomponent

import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.infoholdcity.basearchitecture.self_extends.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (true) {
//            startActivity(Intent(this, NestActivity::class.java))
//            return
//        }


        testRecycleView();
//        testScrollview();

    }

    private fun testRecycleView() {
        rv.layoutManager = LinearLayoutManager(this)
        val myAdapter = SimpleAdapter()
        rv.adapter = myAdapter

        for (i in 0..40) {
            myAdapter.addData(i.toString())
        }
        myAdapter.setOnItemClickListener { adapter, view, position ->
            toast("测试$position")
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

    private fun testScrollview() {
        refreshLayout.setRefreshCallbackListener { refreshLayout ->
            Handler().postDelayed({
                val datas = ArrayList<String>()
                refreshLayout?.freshFinished()
            }, 2000)
        }

        refreshLayout.setLoadCallbackListener { refreshLayout ->
            Handler().postDelayed({
                refreshLayout?.loadFinished()
            }, 1000)

        }
    }

}

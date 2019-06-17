package com.example.learncomponent.fresh

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.learncomponent.R
import com.example.learncomponent.adapter.SimpleAdapter2
import com.infoholdcity.baselibrary.view.freshview.LoadMoreWrapperAdapter
import kotlinx.android.synthetic.main.activity_refresh.*

class RefreshActivity : AppCompatActivity() {
    val datas = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)
        testRecycleView()
    }
    var i=0;
    private fun testRecycleView() {
        rv.layoutManager = LinearLayoutManager(this)
        val simpleAdapter2 = SimpleAdapter2(datas)
        val myAdapter = LoadMoreWrapperAdapter(simpleAdapter2)
        rv.adapter = myAdapter
        for (i in 0..4) {
            datas.add(i.toString())
        }
        myAdapter.notifyDataSetChanged()

        refreshLayout.setCanLoadMore(true)
        refreshLayout.setRefreshCallbackListener { refreshLayout ->
            Handler().postDelayed({
                i = 0;
                datas.clear()
                for (i in 0..3) {
                    datas.add("当前元素" + i)
                }
                myAdapter.notifyDataSetChanged()
                refreshLayout?.freshFinished()
            }, 1000)
        }






        myAdapter.setOnLoadListener(object : LoadMoreWrapperAdapter.OnLoadListener {
            override fun onRetry() {

            }

            override fun onLoadMore() {
                i++
                Handler().postDelayed({
                    if(i<3){
                        for (i in 0..5) {
                            datas.add("新增$i")
                        }
                        myAdapter.loadMoreEnd()
                    }else{
//                        for (i in 0..2) {
//                            datas.add("新增$i")
//                        }
                        myAdapter.loadMoreComplete()
                    }
                }, 1000)
            }

        })


    }

}
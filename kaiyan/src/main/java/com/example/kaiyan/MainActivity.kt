package com.example.kaiyan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import com.example.kaiyan.adapter.GuanzhuAdapter
import com.example.kaiyan.adapter.TestGuanzhuAdapter
import com.example.kaiyan.service.IKaiyanService
import com.example.kaiyan.service.KaiyanServiceImpl
import com.example.kaiyan.widget.media.AndroidMediaController
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {
    var adapter: TestGuanzhuAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter = TestGuanzhuAdapter()
        mRv.adapter = adapter

        LinearSnapHelper().attachToRecyclerView(mRv)

        mRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


            }
        })
        getData()
        button.setOnClickListener {
            play()
        }
    }

    fun play() {
        startActivity(Intent(this@MainActivity, KaiYanAcitivity::class.java))
//        button.layout(button.left, button.top - 100, button.right, button.bottom - 100)
    }



    val service: IKaiyanService by lazy { KaiyanServiceImpl() }


    fun getData() {
        service.getGuanzhuList().excute()
            .subscribe {
                toast(it.size.toString())
                Klog.e(contents = it)
                adapter!!.setNewData(it)


            }
    }

}

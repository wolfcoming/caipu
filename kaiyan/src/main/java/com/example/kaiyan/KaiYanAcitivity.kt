package com.example.kaiyan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.kaiyan.adapter.GuanzhuAdapter
import com.example.kaiyan.service.IKaiyanService
import com.example.kaiyan.service.KaiyanServiceImpl
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import android.support.v7.widget.RecyclerView.*
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kaiyan.*


class KaiYanAcitivity : AppCompatActivity() {

    var adapter: GuanzhuAdapter? = null
    var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kaiyan)
        recycleView.layoutManager = linearLayoutManager
        adapter = GuanzhuAdapter()
        recycleView.adapter = adapter


        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        getData()


        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_IDLE //滚动停止
                    -> {
                        val first = linearLayoutManager.findFirstVisibleItemPosition()
                        val last = linearLayoutManager.findLastVisibleItemPosition()
                        for (i in first..last) {
                            val view = linearLayoutManager.findViewByPosition(i)
                            if (view != null) {
                                if (ifCenter(view)) {
                                    //相关的逻辑操作
                                    adapter?.startVide(i)
                                    Toast.makeText(this@KaiYanAcitivity, i.toString(), Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                    SCROLL_STATE_DRAGGING //手指拖动
                    -> {
                    }
                    SCROLL_STATE_SETTLING //惯性滚动
                    -> {
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Klog.e(contents = "onScrolled: " + recyclerView.scrollY.toString())



            }


        })

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

    private fun ifCenter(view: View): Boolean {
        //获取中间线到顶部的距离，我这里的recyclerview长宽都是等于屏幕宽度，所以可以直接这样计算
        val half_screen = resources.displayMetrics.heightPixels / 2
        //获取中间线到顶部的距离减去它本身高度的大小
        val top_center = half_screen - view.getHeight()
        //获取该item到顶部的距离
        val ruler = view.getTop()
        //直接判断，如果在其中，则返回true，否则false
        return half_screen > ruler && ruler >= top_center
    }
}
package com.example.kaiyan

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
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
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import android.support.v7.widget.RecyclerView.*
import android.view.View
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.kaiyan.remote.ItemBean
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
                                (adapter?.data!![i] as ItemBean).isSelect = ifCenter(view)
                            }
                        }
//                        adapter?.notifyItemRangeChanged(first, last - first + 1)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
        })

        recycleView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                addCenterLine()
                recycleView.viewTreeObserver.removeOnGlobalLayoutListener(this)
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
        //基准线
        val rvCenterLine = (recycleView.top + recycleView.bottom) * 1 / 3

        val viewTop = view.top
        if (viewTop < rvCenterLine) {
            val viewBottom = view.bottom
            return viewBottom > rvCenterLine
        } else return false
    }

    private fun addCenterLine() {
        val rvCenterLine = (recycleView.top + recycleView.bottom) * 1 / 3
        Klog.e(contents = rvCenterLine.toString())
        val viewGroup = recycleView.parent as RelativeLayout
        val viewLine = View(this)
        viewLine.setBackgroundColor(Color.RED)
        viewLine.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4)
        viewGroup.addView(viewLine)
        Handler().postDelayed({
            //            viewLine.layout(viewLine.left, viewLine.top+rvCenterLine, viewLine.right, viewLine.bottom+rvCenterLine)
            val layoutParams: RelativeLayout.LayoutParams = viewLine.layoutParams as RelativeLayout.LayoutParams
            layoutParams.topMargin = rvCenterLine
            viewLine.layoutParams = layoutParams
        }, 300)

    }
}
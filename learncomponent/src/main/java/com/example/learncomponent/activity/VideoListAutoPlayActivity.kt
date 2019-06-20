package com.example.learncomponent.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import com.example.kaiyan.service.IKaiyanService
import com.example.kaiyan.service.KaiyanServiceImpl
import com.example.learncomponent.R
import com.example.learncomponent.adapter.GuanzhuAdapter
import com.example.learncomponent.remote.ItemBean
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_video_list.*

class VideoListAutoPlayActivity : BaseActiviy() {

    var adapter: GuanzhuAdapter? = null
    var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        recycleView.layoutManager = linearLayoutManager
        adapter = GuanzhuAdapter()
        recycleView.adapter = adapter
//        解决刷新闪动问题
        (recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        getData()


        var lastPostion = -1
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE //滚动停止
                    -> {

                        val first = linearLayoutManager.findFirstVisibleItemPosition()
                        val last = linearLayoutManager.findLastVisibleItemPosition()
                        //判断上次播放的位置是否在可见范围内，如果不在则先去关闭上次播放
                        if (lastPostion != -1 && !(lastPostion in first..last)) {
                            Klog.e(contents = "lastPostion： " + lastPostion)
                            (adapter?.data!![lastPostion] as ItemBean).isSelect = false
                            adapter?.notifyItemChanged(lastPostion)
                        }
                        for (i in first..last) {
                            Klog.e(contents = "可见范围： " + i)
                            val view = linearLayoutManager.findViewByPosition(i)
                            if (view != null) {
                                val ifCenter = ifCenter(view)
                                (adapter?.data!![i] as ItemBean).isSelect = ifCenter
                                if (ifCenter) {
                                    lastPostion = i //记录上次播发的位置  在下次播放器关闭
                                }

                            }
                        }

                        adapter?.notifyItemRangeChanged(first, last - first + 1)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        recycleView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                addCenterLine()
                recycleView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }

        })

//        LinearSnapHelper().attachToRecyclerView(recycleView)
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
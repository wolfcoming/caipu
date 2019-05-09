package com.example.kaiyan

import android.os.Bundle
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

class KaiYanAcitivity : BaseActiviy() {

    var adapter :GuanzhuAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recycleView = RecyclerView(this)
        recycleView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT
            , ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContentView(recycleView)
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = GuanzhuAdapter()
        recycleView.adapter = adapter


        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        getData()
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
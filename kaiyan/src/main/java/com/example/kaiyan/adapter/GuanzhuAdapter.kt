package com.example.kaiyan.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kaiyan.R
import com.example.kaiyan.remote.GuanzhuListBean.ItemListBean.DataBeanX.ContentBean.ItemBean
import com.example.kaiyan.widget.media.AndroidMediaController
import com.example.kaiyan.widget.media.IjkVideoView
import com.infoholdcity.basearchitecture.self_extends.Klog

class GuanzhuAdapter : BaseQuickAdapter<ItemBean, BaseViewHolder> {
    private var mMediaController: AndroidMediaController? = null
    private var mVideo: IjkVideoView? = null
    private var mHelper: BaseViewHolder?=null
    override fun convert(helper: BaseViewHolder?, item: ItemBean?) {
        helper!!.setText(R.id.tvTitle, item!!.title)
        mHelper = helper
        val url: String = item.playUrl
//        mVideo = helper.getView<IjkVideoView>(R.id.mVideo)
//        mMediaController = AndroidMediaController(helper.itemView.context, false)
//        mVideo.setMediaController(mMediaController)
//        mVideo?.setVideoPath(url)
//        mVideo?.start()
    }

    constructor() : super(R.layout.item_guanzhu)


    fun startVide(i: Int) {
        Klog.e(contents = "aaaa")
//        mVideo?.start()
        mHelper!!.setText(R.id.tvTitle, "居中了"+i.toString())

        mHelper!!.setBackgroundColor(R.id.mVideo, Color.YELLOW)

    }
}
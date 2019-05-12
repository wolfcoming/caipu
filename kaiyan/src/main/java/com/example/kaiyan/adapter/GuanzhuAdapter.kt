package com.example.kaiyan.adapter

import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kaiyan.R
import com.example.kaiyan.remote.ItemBean
import com.example.kaiyan.widget.media.AndroidMediaController
import com.example.kaiyan.widget.media.IjkVideoView
import com.infoholdcity.basearchitecture.self_extends.Klog

class GuanzhuAdapter : BaseQuickAdapter<ItemBean, BaseViewHolder> {
    private var mVideo: IjkVideoView? = null
    override fun convert(helper: BaseViewHolder?, item: ItemBean?) {
        helper!!.setText(R.id.tvUser, item!!.author.name)
            .setText(R.id.tvTitle, "发布：" + item!!.title)
            .setText(R.id.tvContent, item!!.description)
        val ivheader = helper!!.getView<ImageView>(R.id.ivHeader)
        Glide.with(ivheader.context).load(item.author.icon).into(ivheader)


        val url: String = item.playUrl
        mVideo = helper.getView<IjkVideoView>(R.id.mVideo)
        mVideo?.setVideoPath(url)

        if (item.isSelect) {
            startVide()
        } else {
            recover()
        }


    }

    constructor() : super(R.layout.item_guanzhu)


    fun startVide() {
        mVideo?.start()
    }

    fun recover() {
        mVideo?.pause()
        mVideo?.stopBackgroundPlay()

    }
}
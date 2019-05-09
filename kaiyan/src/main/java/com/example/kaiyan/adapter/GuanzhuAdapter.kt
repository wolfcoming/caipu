package com.example.kaiyan.adapter

import android.widget.TableLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kaiyan.R
import com.example.kaiyan.Settings
import com.example.kaiyan.remote.GuanzhuListBean
import com.example.kaiyan.remote.GuanzhuListBean.ItemListBean.DataBeanX.ContentBean.ItemBean
import com.example.kaiyan.widget.media.AndroidMediaController
import com.example.kaiyan.widget.media.IjkVideoView
import kotlinx.android.synthetic.main.activity_main.*

class GuanzhuAdapter:BaseQuickAdapter<ItemBean,BaseViewHolder> {
    private var mMediaController: AndroidMediaController? = null
    override fun convert(helper: BaseViewHolder?, item: ItemBean?) {
        helper!!.setText(R.id.tvTitle,item!!.title)
        val url:String = item.playUrl
        val mVideo = helper.getView<IjkVideoView>(R.id.mVideo)
        mMediaController = AndroidMediaController(helper.itemView.context, false)
        mVideo.setMediaController(mMediaController)
        mVideo.setVideoPath(url)
        mVideo.start()
    }

    constructor():super(R.layout.item_guanzhu)
}
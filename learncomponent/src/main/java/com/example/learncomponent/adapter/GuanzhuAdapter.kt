package com.example.learncomponent.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.learncomponent.R
import com.example.learncomponent.remote.ItemBean

class GuanzhuAdapter : BaseQuickAdapter<ItemBean, BaseViewHolder> {
    var mVideo: View? = null
    override fun convert(helper: BaseViewHolder?, item: ItemBean?) {
        helper!!.setText(R.id.tvUser, item!!.author.name)
            .setText(R.id.tvTitle, "发布：" + item!!.title)
            .setText(R.id.tvContent, item!!.description)
        val ivheader = helper!!.getView<ImageView>(R.id.ivHeader)
        Glide.with(ivheader.context).load(item.author.icon).into(ivheader)
        val url: String = item.playUrl
        mVideo = helper.getView<View>(R.id.mVideo)
        if (item?.isSelect) {
            mVideo?.setBackgroundColor(Color.YELLOW)
        } else {
            mVideo?.setBackgroundColor(Color.GRAY)
        }
    }

    constructor() : super(R.layout.item_guanzhu)


}
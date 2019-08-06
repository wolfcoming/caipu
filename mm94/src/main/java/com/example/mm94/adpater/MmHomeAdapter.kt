package com.example.mm94.adpater

import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mm94.R
import com.example.mm94.ui.vo.MmBeanVo

class MmHomeAdapter : BaseQuickAdapter<MmBeanVo, BaseViewHolder> {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun convert(helper: BaseViewHolder?, item: MmBeanVo?) {
        helper?.setText(R.id.tvTitle, item?.title)
        val imgUrl = item?.thumimg
        val ivImg = helper?.getView<ImageView>(R.id.ivImg)
        Glide.with(ivImg!!.context)
            .load(imgUrl)
            .into(ivImg)


    }

    constructor() : super(R.layout.item_home)
}
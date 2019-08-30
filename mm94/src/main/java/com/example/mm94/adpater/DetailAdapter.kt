package com.example.mm94.adpater

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mm94.R

class DetailAdapter : BaseQuickAdapter<String, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder, item: String) {
        val sharedOptions = RequestOptions()
            .placeholder(R.drawable.ic_loading_greybg)
            .error(R.drawable.ic_loading_faile)
        Glide.with(helper.itemView.context)
           .load(item)
            .apply(sharedOptions)
            .into(helper.getView(R.id.imageView))


    }

    constructor() : super(R.layout.item_detail)
}
package com.example.mm94.adpater

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mm94.R

class DetailAdapter : BaseQuickAdapter<String, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder, item: String) {
        Glide.with(helper.itemView.context).load(item).into(helper.getView(R.id.imageView))
    }

    constructor() : super(R.layout.item_detail)
}
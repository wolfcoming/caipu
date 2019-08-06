package com.example.app.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.app.R

class MenuAdapter : BaseQuickAdapter<String, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tvContent, item!!)
    }

    constructor() : super(R.layout.itemmenu)
}
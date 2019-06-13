package com.example.learncomponent.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.learncomponent.R

class SimpleAdapter : BaseQuickAdapter<String, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tvcontent,item!!)
    }

    constructor():super(R.layout.item_rv){

    }
}
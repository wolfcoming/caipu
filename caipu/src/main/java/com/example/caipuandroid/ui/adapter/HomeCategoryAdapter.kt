package com.example.caipuandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R

/**
 * Author：yangqing
 * Time：2019/4/16 14:35
 * Description：
 */
class HomeCategoryAdapter : BaseQuickAdapter<String, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.tvName, item)
    }

    constructor() : super(R.layout.item_home_category)

}
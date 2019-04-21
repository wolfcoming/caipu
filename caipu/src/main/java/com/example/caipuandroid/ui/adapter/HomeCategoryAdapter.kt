package com.example.caipuandroid.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.ui.vo.CategoryVo

/**
 * Author：yangqing
 * Time：2019/4/16 14:35
 * Description：
 */
class HomeCategoryAdapter : BaseQuickAdapter<CategoryVo, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: CategoryVo) {
        helper!!.setText(R.id.tvName, item.categoryName)
        val img = helper!!.getView<ImageView>(R.id.ivImage)
        Glide.with(helper!!.itemView).load(item.imgurl).into(img)
    }

    constructor() : super(R.layout.item_home_category)

}
package com.example.shopingmodule.adapter

import android.net.Uri
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.shopingmodule.R
import com.example.shopingmodule.ui.vo.ShopCategoryVo

class TempAdapter : BaseQuickAdapter<ShopCategoryVo, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder?, item: ShopCategoryVo?) {
        helper!!.setText(R.id.tv_name, item!!.name)
        Glide.with(helper.itemView.context).load(Uri.parse(item!!.logo)).into(helper.getView(R.id.iv_logo))
    }

    constructor() : super(R.layout.item_category)
}
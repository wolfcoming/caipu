package com.example.shopingmodule.adapter

import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.shopingmodule.R
import com.example.shopingmodule.ui.vo.ShopCategoryVo

class CategoryAdapter : BaseQuickAdapter<ShopCategoryVo, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder?, item: ShopCategoryVo?) {
        helper!!.setText(R.id.tv_name, item!!.name)
        Glide.with(helper.itemView.context)
            .load(Uri.parse(item!!.logo))
            .into(helper.getView(R.id.iv_logo))

        helper.itemView.setOnClickListener {
            Toast.makeText(helper.itemView.context, "点击了${item.name}", Toast.LENGTH_LONG).show()
        }
    }

    constructor() : super(R.layout.item_category)
}
package com.example.shopingmodule.adapter.home

import android.net.Uri
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.shopingmodule.R
import com.example.shopingmodule.ui.vo.ProductVo
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo

class GoodsSubAdapter : BaseQuickAdapter<ProductVo, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder, item: ProductVo) {
        helper.setText(R.id.tv_branch_name, item.brandName)
            .setText(R.id.tv_keyword, item.keyword)
            .setText(R.id.tv_price, "￥ ${item.price}")
        Glide.with(helper.itemView.context).load(Uri.parse(item.image!!)).into(helper.getView(R.id.iv_image))

    }

    constructor() : super(R.layout.item_child_goods)
}
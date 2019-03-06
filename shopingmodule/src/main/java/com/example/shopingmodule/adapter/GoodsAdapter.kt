package com.example.shopingmodule.adapter

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.shopingmodule.R
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo

class GoodsAdapter : BaseQuickAdapter<ShopBannerVo, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder, item: ShopBannerVo) {
        helper.setText(R.id.tv_name, item.name)
            .setText(R.id.tv_title, item.title)
        Glide.with(helper.itemView.context).load(Uri.parse(item.image!!)).into(helper.getView(R.id.iv_image))
        val recyclerView = helper.getView<RecyclerView>(R.id.rl_goods)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = GoodsSubAdapter()
        recyclerView.adapter = adapter
        adapter.setNewData(item.items)
    }

    constructor() : super(R.layout.item_goods)
}
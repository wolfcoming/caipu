package com.example.caipuandroid.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.remote.URLConfig
import com.example.caipuandroid.ui.vo.Greens
import com.example.componentbase.ServiceFactory
import java.lang.Exception

class GoodsListAdapter : BaseQuickAdapter<Greens, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: Greens) {
       val name =  ServiceFactory.instance.getUsercenterService().getUserName()
        helper!!.setText(R.id.tvName, item!!.name)
            .setText(R.id.tvViews,item.views.toString()+"浏览")
            .setText(R.id.tvBurden,item.burden.toString())
            .setText(R.id.tvCollects,item.collect.toString()+"收藏")
            .setText(R.id.tvAuthor,name)
        val ivCover = helper!!.getView<ImageView>(R.id.ivCover)
        try {
            var imgStr = item.img
            if (imgStr!!.startsWith("http")) {
                Glide.with(helper!!.itemView.context).load(imgStr).into(ivCover)
            }else{
                imgStr =  URLConfig.qiniuBaseurl +imgStr
                Glide.with(helper!!.itemView.context).load(imgStr).into(ivCover)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    constructor() : super(R.layout.item_goodslist)
}
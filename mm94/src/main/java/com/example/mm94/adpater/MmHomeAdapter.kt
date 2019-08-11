package com.example.mm94.adpater

import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mm94.R
import com.example.mm94.ui.vo.MmBeanVo
import com.infoholdcity.basearchitecture.self_extends.log
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.request.RequestOptions


class MmHomeAdapter : BaseQuickAdapter<MmBeanVo, BaseViewHolder> {

    var itemView: View? = null
    override fun convert(helper: BaseViewHolder, item: MmBeanVo?) {
        itemView = helper.itemView
//        设置文字内容
        helper?.setText(R.id.tvTitle, item?.title)


        val imgUrl = item?.thumimg
        val ivImg = helper?.getView<ImageView>(R.id.ivImg)
        val layoutParams = helper.itemView.layoutParams
        var sharedOptions: RequestOptions? = null
//        展示不同样式
        if (style == 0) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            helper.itemView.layoutParams = layoutParams
            sharedOptions = RequestOptions()
                .placeholder(R.drawable.ic_loading_greybg)
                .override(style1Width, style1Height)
                .error(R.drawable.ic_loading_faile)

        } else {
            helper.itemView.height.log()
            layoutParams.height = 540
            helper.itemView.layoutParams = layoutParams
            sharedOptions = RequestOptions()
                .placeholder(R.drawable.ic_loading_greybg)
                .error(R.drawable.ic_loading_faile)
        }

        Glide.with(helper.itemView.context)
            .load(imgUrl)
            .apply(sharedOptions)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    ("请求失败" + item?.title).log()
//                        ivImg.setBackgroundResource(R.drawable.icon_failed)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .error(R.drawable.ic_loading_faile)
            .into(ivImg!!)


    }

    var style: Int = 0
    var style1Height = 0
    var style1Width = 0

    fun setmStyle(i: Int) {
        //记录之前 宽高
        if (i == 1) {
            style1Height = itemView!!.measuredHeight
            style1Width = itemView!!.measuredWidth
            "开始".log()
            style1Height.log()
        }else{

        }
        this.style = i
    }


    constructor() : super(R.layout.item_home)
}
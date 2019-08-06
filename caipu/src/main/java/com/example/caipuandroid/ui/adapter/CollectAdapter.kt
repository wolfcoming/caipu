package com.example.caipuandroid.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.db.AppDatabase
import com.example.caipuandroid.db.CollectEntity
import com.example.caipuandroid.remote.URLConfig
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.log
import com.infoholdcity.baselibrary.config.ARouterConfig
import io.reactivex.Observable
import java.lang.Exception
import kotlin.concurrent.thread

class CollectAdapter : BaseQuickAdapter<CollectEntity, BaseViewHolder> {
    @SuppressLint("CheckResult")
    override fun convert(helper: BaseViewHolder?, item: CollectEntity?) {
        helper!!.setText(R.id.tvName, item!!.name)
            .setText(R.id.tvViews, item.views.toString() + "浏览")
            .setText(R.id.tvBurden, item.burden.toString())
            .setText(R.id.tvCollects, item.collect.toString() + "收藏")

        val ivCover = helper.getView<ImageView>(R.id.ivCover)
        try {
            var imgStr = item.img
            if (imgStr!!.startsWith("http")) {
                Glide.with(helper.itemView.context).load(imgStr).into(ivCover)
            } else {
                imgStr = URLConfig.qiniuBaseurl + imgStr
                Glide.with(helper.itemView.context).load(imgStr).into(ivCover)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        helper.getView<LinearLayout>(R.id.llcontain).setOnClickListener {
            val entity = item
            val greens = Greens()
            entity.let {
                greens.name = it.name
                greens.brief = it.brief
                greens.burden = it.burden
                greens.collect = it.collect
                greens.id = it.id
                greens.img = it.img
                greens.makes = it.makes
                greens.tips = it.tips
                greens.views = it.views
                greens
            }
            val bundle = Bundle()
            bundle.putSerializable("Greens", greens)
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_DETAIL)
                .with(bundle)
                .navigation()
        }
        helper.getView<Button>(R.id.btnDel).setOnClickListener {
            Observable.create<Int> { it ->
                val count = AppDatabase.getCollectDao().deletCollectByid(item.id)
                it.onNext(count)
            }.excute()
                .subscribe {
                    Toast.makeText(
                        ivCover!!.context,
                        if (it > 0) "删除成功" else "删除失败",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }

    }

    constructor() : super(R.layout.item_collect)
}
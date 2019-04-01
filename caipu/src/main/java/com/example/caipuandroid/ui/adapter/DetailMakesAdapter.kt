package com.example.caipuandroid.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.remote.URLConfig
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.MakesBean
import kotlinx.android.synthetic.main.activity_goodsdetail.*

class DetailMakesAdapter : BaseQuickAdapter<MakesBean, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder?, item: MakesBean?) {

        val tvStep = helper!!.getView<TextView>(R.id.tvStep)
        val ivStepImg = helper!!.getView<ImageView>(R.id.ivStepImg)
        helper.setText(R.id.tvOrder, (helper.adapterPosition + 1).toString() + ".")
        if (item!!.step.isNotEmpty()) {
            tvStep.setText(item?.step)
        } else tvStep.setText("")
        var imagUrl = item.stepImg!!
        if (!imagUrl!!.startsWith("http")) {
            imagUrl = URLConfig.qiniuBaseurl + imagUrl
        }
        Glide.with(helper.itemView.context).load(Uri.parse(imagUrl)).into(ivStepImg)

    }


    constructor() : super(R.layout.item_makes_detail)


}
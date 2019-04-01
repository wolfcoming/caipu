package com.example.caipuandroid.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.ui.vo.BurdenBean

class DetailBurdenAdapter : BaseQuickAdapter<BurdenBean, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: BurdenBean?) {

        val tvName = helper!!.getView<TextView>(R.id.tvName)
        val tvDosage = helper!!.getView<TextView>(R.id.tvDosage)
        if (item!!.name.isNotEmpty()) {
            tvName.setText(item?.name)
        } else tvName.setText("")
        if (item!!.dosage.isNotEmpty()) {
            tvDosage.setText(item?.dosage)
        } else tvDosage.setText("")
    }

    constructor() : super(R.layout.item_burden_detail)


}
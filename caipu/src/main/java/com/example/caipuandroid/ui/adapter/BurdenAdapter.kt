package com.example.caipuandroid.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.ui.vo.BurdenBean

class BurdenAdapter : BaseQuickAdapter<BurdenBean, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: BurdenBean?) {

        val etName = helper!!.getView<EditText>(R.id.etName)
        val etDosage = helper!!.getView<EditText>(R.id.etDosage)
        if (item!!.name.isNotEmpty()) {
            etName.setText(item?.name)
        } else etName.setText("")
        if (item!!.dosage.isNotEmpty()) {
            etDosage.setText(item?.dosage)
        } else etDosage.setText("")

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty())
                    data.get(helper.adapterPosition).name = s.toString()
            }

        })
        etDosage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty())
                    data.get(helper.adapterPosition).dosage = s.toString()
            }
        })
    }

    constructor() : super(R.layout.item_burden)


}
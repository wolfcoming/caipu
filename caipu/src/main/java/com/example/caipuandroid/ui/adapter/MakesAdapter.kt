package com.example.caipuandroid.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.MakesBean

class MakesAdapter : BaseQuickAdapter<MakesBean, BaseViewHolder> {

    override fun convert(helper: BaseViewHolder?, item: MakesBean?) {

        val etStep = helper!!.getView<EditText>(R.id.etStep)
        val ivStepImg = helper!!.getView<ImageView>(R.id.ivStepImg)
        helper.setText(R.id.tvOrder, (helper.adapterPosition+1).toString() + ".")
        if (item!!.step.isNotEmpty()) {
            etStep.setText(item?.step)
        } else etStep.setText("")


        etStep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty())
                    data.get(helper.adapterPosition).step = s.toString()
            }
        })

        ivStepImg?.setOnClickListener {
            imageClick?.imageClick(ivStepImg!!, helper.adapterPosition)
        }
    }

    var imageClick: IImageClick? = null;

    constructor() : super(R.layout.item_makes)

    open interface IImageClick {
        fun imageClick(imageView: ImageView, position: Int)
    }


}
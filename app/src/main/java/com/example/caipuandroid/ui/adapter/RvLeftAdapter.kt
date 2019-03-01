package com.example.caipuandroid.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.ui.vo.CategoryVo

class RvLeftAdapter : BaseQuickAdapter<CategoryVo, BaseViewHolder> {

        constructor() : super(R.layout.item_category_left)

        override fun convert(helper: BaseViewHolder?, item: CategoryVo?) {
            helper!!.setText(R.id.tvContent, item!!.categoryName)
            if (item.categoryLevel == 1) {
                if (item.isSelect) {
                    helper!!.setBackgroundColor(R.id.tvContent, Color.WHITE)
                    helper!!.setTextColor(R.id.tvContent, Color.RED)
                } else {
                    helper!!.setBackgroundColor(R.id.tvContent, Color.parseColor("#eeeeee"))
                    helper!!.setTextColor(R.id.tvContent, Color.BLACK)
                }
            }

        }
    }
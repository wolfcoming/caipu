package com.example.shopingmodule.ui

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.shopingmodule.R
import com.infoholdcity.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentA : BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragment_a
    }

    override fun initView(anchor: View) {
        recycleview.layoutManager = LinearLayoutManager(context)
        val adapter = TempAdapter()
        recycleview.adapter = adapter
        val datas = ArrayList<Int>()
        for (i in 0..100) {
            datas.add(i)
        }
        adapter.setNewData(datas)
    }

    inner class TempAdapter : BaseQuickAdapter<Int, BaseViewHolder> {
        override fun convert(helper: BaseViewHolder?, item: Int?) {
            helper!!.setText(R.id.tv_content, item!!.toString())
        }
        constructor() : super(R.layout.temp_item)
    }

}
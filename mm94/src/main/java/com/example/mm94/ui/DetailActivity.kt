package com.example.mm94.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.WindowManager
import com.example.mm94.R
import com.example.mm94.adpater.DetailAdapter
import com.example.mm94.ui.vo.MmBeanVo
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val mmBeanVo: MmBeanVo = intent.extras["MM"] as MmBeanVo
        rvDetail.layoutManager = LinearLayoutManager(this)
        val mAdapter = DetailAdapter()
        rvDetail.adapter = mAdapter
        mAdapter.setNewData(mmBeanVo.images)


        mAdapter.setOnItemClickListener { adapter, view, position ->
            ImageShowActivity.startImageShowActivity(this@DetailActivity, adapter.getItem(position) as String)
        }

//        val snaphelper = PagerSnapHelper()
//        snaphelper.attachToRecyclerView(rvDetail)
    }
}
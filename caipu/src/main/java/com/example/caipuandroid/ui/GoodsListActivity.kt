package com.example.caipuandroid.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.caipuandroid.R
import com.example.caipuandroid.service.ICaipuService
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.GoodsListAdapter
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig.Companion.ACT_CAIPU_LIST
import kotlinx.android.synthetic.main.activity_goodslist.*

@Route(path = ACT_CAIPU_LIST)
class GoodsListActivity : BaseActiviy() {
    val service by lazy { IServiceNetImpl() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goodslist)
        rvGoodsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = GoodsListAdapter()
        rvGoodsList.adapter = adapter

        service.getGreensList(10, 1)
            .excute()
            .subscribe({
                adapter.addData(it)
            }, {
                toast(it.message!!)
            })

    }
}
package com.example.caipuandroid.ui

import android.content.Intent
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
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_goodslist.*

@Route(path = ACT_CAIPU_LIST)
class GoodsListActivity : BaseActiviy() {
    val service by lazy { IServiceNetImpl() }
    val adapter = GoodsListAdapter()
    var pageSize = 15
    var pageNumber = 1
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goodslist)
        name = intent.getStringExtra("name")
        etName.setText(name)
        etName.setSelection(name.length)
        etName.clearFocus()

        rvGoodsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGoodsList.adapter = adapter
        getData(true)
        refreshLayout.setOnLoadMoreListener { refreshLayout ->
            getData(false)
        }

        refreshLayout.setOnRefreshListener {
            getData(true)
        }
        tvSearch.setOnClickListener {
            name = etName.text.toString()
            getData(true)
        }
        ivBack.setOnClickListener { finish() }
    }

    fun getData(isFresh: Boolean) {
        if (isFresh) {
            pageNumber = 1
        }
        service.getGreensList(pageSize, pageNumber, name)
            .excute()
            .subscribe({
                pageNumber++
                if (isFresh) {
                    refreshLayout.finishRefresh()
                    adapter.setNewData(it)
                } else {
                    if (pageSize > it.size) {
                        refreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        refreshLayout.finishLoadMore()
                    }
                    adapter.addData(it)
                }
            }, {
                refreshLayout.finishLoadMore()
                refreshLayout.finishRefresh()
                toast(it.message!!)
            })
    }


}
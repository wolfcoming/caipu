package com.example.caipuandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.caipuandroid.R
import com.example.caipuandroid.service.ICaipuService
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.GoodsListAdapter
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.config.ARouterConfig.Companion.ACT_CAIPU_LIST
import com.infoholdcity.baselibrary.view.SingleProgressDialog
import com.infoholdcity.baselibrary.view.muiltview.Gloading
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
        val showKeyBoard = intent.getBooleanExtra("showKeyBoard",false)
        etName.setText(name)

        if(!showKeyBoard){
            etName.setSelection(name.length)
            etName.clearFocus()
        }else{
            etName.isFocusable = true
            etName.isFocusableInTouchMode = true
            etName.requestFocus()

        }




        rvGoodsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGoodsList.adapter = adapter

        //在refreshLayout 界面显示不同状态界面
        initMuiltStatusArea(refreshLayout)
        showLoadingStatus()
        getData(true)
        refreshLayout.setOnLoadMoreListener { refreshLayout ->
            getData(false)
        }

        refreshLayout.setOnRefreshListener {
            getData(true)
        }
        tvSearch.setOnClickListener {
            showLoadingStatus()
            name = etName.text.toString()
            getData(true)
        }
        ivBack.setOnClickListener { finish() }


        adapter.setOnItemClickListener { adapter, view, position ->
            val greens = adapter.getItem(position) as Greens
            val bundle = Bundle()
            bundle.putSerializable("Greens",greens)
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_DETAIL)
                .with(bundle)
                .navigation()
        }
    }

    @SuppressLint("CheckResult")
    fun getData(isFresh: Boolean) {
        if (isFresh) {
            pageNumber = 1
        }
        service.getGreensList(pageSize, pageNumber, name)
            .excute()
            .subscribe({
                showLoadSuccessStatus()
                pageNumber++
                if (isFresh) {
                    refreshLayout.finishRefresh()
                    adapter.setNewData(it)
                    if (it.size == 0) {
                        showEmptyStatus()
                    }
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
                showLoadFailedStatus()
                toast(it.message!!)
            })
    }


    override fun onLoadRetry() {
        showLoadingStatus()
        getData(false)
    }
}
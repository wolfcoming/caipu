package com.example.mm94.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mm94.R
import com.example.mm94.adpater.MmHomeAdapter
import com.example.mm94.service.impl.MmService
import com.example.mm94.ui.vo.MmBeanVo
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_mm94_main.*


@Route(path = ARouterConfig.ACT_MM94HOME)
class MM94MainActivity : BaseActiviy() {

    var subscribe: Disposable? = null
    val service: MmService by lazy { MmService() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mm94_main)
        initView()
    }

    val mAdapter: MmHomeAdapter by lazy { MmHomeAdapter() }
    private fun initView() {
        mRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRv.adapter = mAdapter
        getData()


        mAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this@MM94MainActivity, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("MM", (adapter.getItem(position) as MmBeanVo))
            intent.putExtras(bundle)
            startActivity(intent)

        }

    }

    fun getData() {
        subscribe = service.getMmList().excute().subscribe({
            mAdapter.setNewData(it)
        }, {
            toast(it.message!!)
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
    }

}

package com.example.caipuandroid.ui

import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.caipuandroid.R
import com.example.caipuandroid.remote.URLConfig
import com.example.caipuandroid.service.ICaipuService
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.DetailBurdenAdapter
import com.example.caipuandroid.ui.adapter.DetailMakesAdapter
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.Greens
import com.example.caipuandroid.ui.vo.MakesBean
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.activity_goodsdetail.*


@Route(path = ARouterConfig.ACT_CAIPU_DETAIL)
class GoodsDetailActivity : BaseActiviy() {
    val adapter = DetailBurdenAdapter()
    val makeAdapter = DetailMakesAdapter()
    val service: ICaipuService by lazy { IServiceNetImpl() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goodsdetail)

        rvBundls.adapter = adapter
        rvBundls.layoutManager = LinearLayoutManager(this)
        rvMakes.adapter = makeAdapter
        rvMakes.layoutManager = LinearLayoutManager(this)

        val greens = intent.getSerializableExtra("Greens") as Greens
        service.getGreensById(greens.id)
            .excute()
            .subscribe({
                dealData(it)
            }, {})


    }

    private fun dealData(it: Greens) {
        var imagUrl = it.img
        if (!imagUrl!!.startsWith("http")) {
            imagUrl = URLConfig.qiniuBaseurl + imagUrl
        }
        Glide.with(this).load(Uri.parse(imagUrl)).into(ivCover)
        tvName.text = it.name
        tvTips.text = it.tips
        val viewsAndCollect = "独家~${it.views}浏览~${it.collect}收藏"
        tvViewsAndCollects.text = viewsAndCollect

        val burden = it.burden
        var burdenList: ArrayList<BurdenBean> = ArrayList()
        for (item in burden!!.split(";")) {
            if (item.isNotBlank() and item.isNotEmpty()) {
                val split = item.split(":")
                val burdenBean = BurdenBean(split[0], split[1])
                burdenList.add(burdenBean)
            }
        }
        adapter.setNewData(burdenList)
        var makesList: ArrayList<MakesBean> = ArrayList()
        for (item in it.makes!!.split("||")) {
            if (item.isNotBlank() and item.isNotEmpty()) {
                val split = item.split("&&")
                val makesBean = MakesBean(split[0], split[1])
                makesList.add(makesBean)
            }
        }

        makeAdapter.setNewData(makesList)

    }
}
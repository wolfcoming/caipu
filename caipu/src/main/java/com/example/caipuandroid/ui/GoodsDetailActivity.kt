package com.example.caipuandroid.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.caipuandroid.R
import com.example.caipuandroid.db.AppDatabase
import com.example.caipuandroid.db.CollectEntity
import com.example.caipuandroid.remote.URLConfig
import com.example.caipuandroid.service.ICaipuService
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.DetailBurdenAdapter
import com.example.caipuandroid.ui.adapter.DetailMakesAdapter
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.Greens
import com.example.caipuandroid.ui.vo.MakesBean
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.log
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_goodsdetail.*
import kotlin.concurrent.thread


@Route(path = ARouterConfig.ACT_CAIPU_DETAIL)
class GoodsDetailActivity : BaseActiviy() {
    val burdenData = ArrayList<BurdenBean>()
    val adapter = DetailBurdenAdapter(burdenData)
    val makeAdapter = DetailMakesAdapter()
    val service: ICaipuService by lazy { IServiceNetImpl() }

    var item: Greens? = null

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
            }, {
                toast(it.message!!)
            })





        toobar.alpha = 0f
        scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY <= tvName.y) {
                    val rate: Float = scrollY / tvName.y
                    toobar.alpha = rate
                } else {
                    if (toobar.alpha != 1f) {
                        toobar.alpha = 1f
                    }
                }
            }
        })


    }

    var subscribe: Disposable? = null
    private fun dealData(it: Greens) {
        item = it
        if (it.user != null) {
            llUserInfo.visibility = View.VISIBLE
            it.user!!.let {
                tvUserBrief.text = it.brief
                tvUserName.text = it.name
                if (it.heading.isNotEmpty())
                    Glide.with(this@GoodsDetailActivity).load(it.heading).into(ivUserHead)
            }
        } else {
            llUserInfo.visibility = View.GONE
        }

        var imagUrl = it.img
        if (!imagUrl!!.startsWith("http")) {
            imagUrl = URLConfig.qiniuBaseurl + imagUrl
        }
        Glide.with(this).load(Uri.parse(imagUrl)).into(ivCover)
        tvName.text = it.name
        toobar.setTitle(it.name!!)
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
        burdenData.clear()
        burdenData.addAll(burdenList)
        adapter.notifyDataSetChanged()
        var makesList: ArrayList<MakesBean> = ArrayList()
        for (item in it.makes!!.split("||")) {
            if (item.isNotBlank() and item.isNotEmpty()) {
                val split = item.split("&&")
                val makesBean = MakesBean(split[0], split[1])
                makesList.add(makesBean)
            }
        }
        makeAdapter.setNewData(makesList)

        //处理收藏逻辑
        subscribe = AppDatabase.getCollectDao().getCollectByid(it.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    btnCollect.setText("取消收藏")
                } else {
                    btnCollect.setText("收藏")
                }
            }, {
                toast(it.message!!)
            })



        btnCollect.setOnClickListener {
            if (btnCollect.text.trim().equals("收藏")) {
                thread {
                    val collectEntity = CollectEntity()
                    collectEntity.id = item?.id!!
                    collectEntity.brief = item?.brief
                    collectEntity.burden = item?.burden
                    collectEntity.collect = item?.collect!!
                    collectEntity.img = item?.img
                    collectEntity.makes = item?.makes
                    collectEntity.name = item?.name
                    collectEntity.tips = item?.tips
                    collectEntity.views = item?.views!!
                    AppDatabase.getCollectDao().insertCollectGreen(collectEntity)

                    runOnUiThread {
                        btnCollect.setText("取消收藏")
                    }
                }
            } else if (btnCollect.text.trim().equals("取消收藏")) {
                thread {
                    val lines = AppDatabase.getCollectDao().deletCollectByid(item?.id!!)
                    if(lines>0){
                        runOnUiThread {
                            btnCollect.setText("收藏")
                        }
                    }
                }
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        subscribe?.let {
            it.dispose()
        }

    }


}
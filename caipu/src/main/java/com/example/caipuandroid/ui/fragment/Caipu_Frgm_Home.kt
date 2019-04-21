package com.example.caipuandroid.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.example.caipuandroid.R
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.GoodsListAdapter
import com.example.caipuandroid.ui.adapter.HomeCategoryAdapter
import com.example.caipuandroid.ui.loader.GlideImageLoader
import com.example.caipuandroid.ui.vo.Greens
import com.example.componentbase.eventbus.SlideMenuEvent
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.caipu_frgm_category.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

class Caipu_Frgm_Home : BaseFragment() {

    val service by lazy { IServiceNetImpl() }

    override fun inflateView(): Any {
        return R.layout.caipu_frgm_category
    }

    override fun initView(anchor: View) {

        initBanner()
        initCategory()
        initGoodsList()

    }

    private fun initGoodsList() {
        val goodsListAdapter = GoodsListAdapter()
        rvGoods.layoutManager = LinearLayoutManager(context)
        rvGoods.adapter = goodsListAdapter


        val goodsList = ArrayList<Greens>()

        for (i in 1..10) {
            val greens = Greens()
            greens.brief = "测试"
            greens.name = "测试"

            goodsList.add(greens)
        }
        goodsListAdapter.setNewData(goodsList)
    }

    /**
     * 分类
     */
    private fun initCategory() {
        rvCategory.layoutManager = GridLayoutManager(context, 5)
        var adapter = HomeCategoryAdapter()
        rvCategory.adapter = adapter

        val catagorys = ArrayList<String>()
        for (i in 1..10) {
            catagorys.add(i.toString())
        }
        adapter.setNewData(catagorys)

    }

    private fun initBanner() {
        val imagelist = ArrayList<String>()
        service.getBannerData().excute().subscribe ({it->
            it.map {
                imagelist.add(it.img)
            }
            banner.setImageLoader(GlideImageLoader())
            banner.setImages(imagelist)
            banner.start();
            banner.setOnBannerListener { position ->
                toast(it[position].name)
            }
        },{
            toast(it.message!!)
        })



    }

}
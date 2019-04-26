package com.example.caipuandroid.ui.fragment

import android.graphics.PorterDuffXfermode
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.caipuandroid.R
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.GoodsListAdapter
import com.example.caipuandroid.ui.adapter.HomeCategoryAdapter
import com.example.caipuandroid.ui.loader.GlideImageLoader
import com.example.caipuandroid.ui.vo.CategoryVo
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.caipu_frgm_category.*

class Caipu_Frgm_Home : BaseFragment() {

    val service by lazy { IServiceNetImpl() }

    override fun inflateView(): Any {
        return R.layout.caipu_frgm_category
    }

    override fun initView(anchor: View) {
        llSearch.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_LIST)
                .withString("name", "")
                .withBoolean("showKeyBoard",true)
                .navigation()
        }
        initBanner()
        initCategory()
        initGoodsList()
    }

    private fun initGoodsList() {
        val goodsListAdapter = GoodsListAdapter()
        rvGoods.layoutManager = LinearLayoutManager(context)
        rvGoods.adapter = goodsListAdapter
        service.getGreensList(20, 1, "")
            .excute()
            .subscribe({
                goodsListAdapter.setNewData(it)
            }, {
                toast(it.message!!)
            })

        goodsListAdapter.setOnItemClickListener { adapter, view, position ->
            val bundle = Bundle()
            bundle.putSerializable("Greens", adapter.data[position] as Greens)
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_DETAIL)
                .with(bundle)
                .navigation()
        }
    }

    /**
     * 分类
     */
    private fun initCategory() {
        rvCategory.layoutManager = GridLayoutManager(context, 5)
        var adapter = HomeCategoryAdapter()
        rvCategory.adapter = adapter
        service.getCategorys()
            .excute()
            .map {
                if (it.size > 10) {
                    it.subList(0, 10)
                } else
                    it
            }
            .subscribe({ it ->
                adapter.setNewData(it)
            }, {
                toast(it.message!!)
            })

        adapter.setOnItemClickListener { adapter, view, position ->
            val categoryVo = adapter!!.getItem(position)!! as CategoryVo
            //跳转到列表界面 传递参数
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_LIST)
                .withString("name", categoryVo!!.categoryName)
                .navigation()
        }
    }

    private fun initBanner() {
        val imagelist = ArrayList<String>()
        service.getBannerData().excute().subscribe({ it ->
            it.map {
                imagelist.add(it.img)
            }
            banner.setImageLoader(GlideImageLoader())
            banner.setImages(imagelist)
            banner.start();
            banner.setOnBannerListener { position ->
                //跳转到列表界面 传递参数
                ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_LIST)
                    .withString("name", it[position].name)
                    .navigation()
            }
        }, {
            toast(it.message!!)
        })
    }

}
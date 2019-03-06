package com.example.shopingmodule.ui

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.shopingmodule.R
import com.example.shopingmodule.adapter.CategoryAdapter
import com.example.shopingmodule.adapter.ContentBean
import com.example.shopingmodule.adapter.HomeAdapter
import com.example.shopingmodule.mvp.HomeContract
import com.example.shopingmodule.mvp.HomePresenter
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<HomeContract.Ipresenter>(), HomeContract.IHomeView {
    var tempAdapter: HomeAdapter? = null


    override fun showData(datas: HashMap<String, Any>) {
        val categorys = datas["category"] as ArrayList<ShopCategoryVo>
        val bannerList = datas["bannerlist"] as ArrayList<ShopBannerVo>

        val datas: ArrayList<ContentBean> = ArrayList()
        datas.add(ContentBean(1, categorys = categorys))
        datas.add(ContentBean(2, goods = bannerList))
        tempAdapter!!.setNewDatas(datas)
    }

    override fun getPresenter(): HomeContract.Ipresenter {
        return HomePresenter()
    }

    override fun attachView(presenter: HomeContract.Ipresenter) {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView.layoutManager = LinearLayoutManager(this)
        tempAdapter = HomeAdapter()
        recycleView.adapter = tempAdapter
        mPresenter.getData()
    }


    override fun onError(message: String) {
        super.onError(message)
        toast(message)
    }
}

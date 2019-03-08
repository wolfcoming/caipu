package com.example.shopingmodule.ui

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.shopingmodule.R
import com.example.shopingmodule.ScreenUtil
import com.example.shopingmodule.adapter.home.ContentBean
import com.example.shopingmodule.adapter.home.HomeAdapter
import com.example.shopingmodule.mvp.HomeContract
import com.example.shopingmodule.mvp.HomePresenter
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import com.infoholdcity.basearchitecture.self_extends.toast
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_search_title.*


class MainActivity : BaseMvpActivity<HomeContract.Ipresenter>(), HomeContract.IHomeView {
    var tempAdapter: HomeAdapter? = null


    var headerHegiht: Int? = 0
    override fun showData(datas: HashMap<String, Any>) {
        val categorys = datas["category"] as ArrayList<ShopCategoryVo>
        val bannerList = datas["bannerlist"] as ArrayList<ShopBannerVo>

        val datas: ArrayList<ContentBean> = ArrayList()
        datas.add(ContentBean(1, categorys = categorys))
        datas.add(ContentBean(2, goods = bannerList))
        tempAdapter!!.setNewDatas(datas)


        //添加底部fragment
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this)
                .add("精选", FragmentA::class.java)
                .add("大家喜欢", FragmentB::class.java)
                .create()
        )



        headerHegiht = search_contain.height
        vp_shoping.adapter = adapter
        tablayout.setViewPager(vp_shoping)
//        设置viewpage的高度
        val params = vp_shoping.layoutParams
        params.height = ScreenUtil.getScreenHeight(this) - headerHegiht!! - rl_tablayout.height
        vp_shoping.layoutParams = params
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

        initLinstener()
//        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        tempAdapter = HomeAdapter()
        recycleView.adapter = tempAdapter
        mPresenter.getData()
    }

    private fun initLinstener() {

        scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            internal var lastScrollY = 0

            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {

            }
        })
    }


    override fun onError(message: String) {
        super.onError(message)
        toast(message)
    }
}

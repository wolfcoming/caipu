package com.example.shopingmodule.ui

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
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
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.shop_activity_main.*
import kotlinx.android.synthetic.main.common_search_title.*


@Route(path = ARouterConfig.ACT_SHOP_HOME)
class ShopMainActivity : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        supportFragmentManager.beginTransaction().add(R.id.shop_frgm, Frgm_ShopMain()).commit()
    }

}

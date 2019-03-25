package com.example.caipuandroid.ui

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.caipuandroid.R
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.caipuandroid.mvp.contract.CategoryContract
import com.example.caipuandroid.mvp.p.CategoryPresenter
import com.example.caipuandroid.ui.adapter.RvLeftAdapter
import com.example.caipuandroid.ui.fragment.Frgm_Category
import com.example.caipuandroid.ui.vo.CategoryVo
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.view.SingleProgressDialog
import kotlinx.android.synthetic.main.activity_category.*


@Route(path = ARouterConfig.ACT_CAIPU_CATEGORY)
class CategoryActivity : BaseActiviy(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.common_fragment,Frgm_Category())
        beginTransaction.commit()

    }
}
package com.example.shopingmodule.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.shopingmodule.R
import com.example.shopingmodule.adapter.TempAdapter
import com.example.shopingmodule.mvp.HomeContract
import com.example.shopingmodule.mvp.HomePresenter
import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<HomeContract.Ipresenter>(), HomeContract.IHomeView {
    var tempAdapter: TempAdapter? = null


    override fun showData(datas: HashMap<String, Any>) {
        val categorys = datas["category"] as ArrayList<ShopCategoryVo>
        val bannerList = datas["bannerlist"] as ArrayList<ShopBannerVo>
        tempAdapter!!.setNewData(categorys)
        Klog.e(contents = categorys)
        Klog.e(contents = bannerList)
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
        recycleView.layoutManager = GridLayoutManager(this, 4)
        tempAdapter = TempAdapter()
        recycleView.adapter = tempAdapter
        mPresenter.getData()
    }


    override fun onError(message: String) {
        super.onError(message)
        toast(message)
    }
}

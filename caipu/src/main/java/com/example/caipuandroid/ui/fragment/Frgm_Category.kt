package com.example.caipuandroid.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.example.caipuandroid.R
import com.example.caipuandroid.base.BaseMvpFragment
import com.example.caipuandroid.mvp.contract.CategoryContract
import com.example.caipuandroid.mvp.p.CategoryPresenter
import com.example.caipuandroid.ui.adapter.RvLeftAdapter
import com.example.caipuandroid.ui.vo.CategoryVo
import com.example.componentbase.eventbus.UserEvent
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.view.SingleProgressDialog
import com.infoholdcity.baselibrary.view.muiltview.Gloading
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.frgm_category.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class Frgm_Category : BaseMvpFragment<CategoryContract.Presenter>(), CategoryContract.View {

    override fun getPresenter(): CategoryContract.Presenter {
        return CategoryPresenter()
    }

    override fun attachView(presenter: CategoryContract.Presenter) {
        presenter.attachView(this)
    }

    override fun inflateView(): Any {
        return R.layout.frgm_category
    }

    var holder: Gloading.Holder? = null
    override fun initView(anchor: View) {
        holder = Gloading.getDefault().wrap(llRootView).withRetry {
            toast("重新请求")
        }
        initView()
        mPresenter.getCategoryData()
    }

    var leftAdapter: RvLeftAdapter? = null
    var rightAdapter: RvLeftAdapter? = null
    private fun initView() {
        rvLeft.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        leftAdapter = RvLeftAdapter()
        rvLeft.adapter = leftAdapter
        leftAdapter!!.setOnItemClickListener { adapter, view, position ->
            val categoryVo = leftAdapter!!.getItem(position)!!
            leftAdapter!!.data.forEachIndexed { index, categoryVo ->
                categoryVo.isSelect = index == position
            }
            leftAdapter!!.notifyDataSetChanged()
            showRightData(categoryVo.subCategorys)
        }

        rvRight.layoutManager = GridLayoutManager(context, 3)
        rightAdapter = RvLeftAdapter()
        rvRight.adapter = rightAdapter
        rightAdapter!!.setOnItemClickListener { adapter, view, position ->
            val categoryVo = rightAdapter!!.getItem(position)!!
            //跳转到列表界面 传递参数
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_LIST)
                .withString("name", categoryVo.categoryName!!)
                .navigation()
        }
    }


    fun showRightData(list: ArrayList<CategoryVo>) {
        rightAdapter!!.setNewData(list)
    }

    override fun showLoading() {
        holder!!.showLoading()
    }

    override fun hideLoading() {
        holder!!.showLoadSuccess()
    }

    override fun onError(message: String) {
        holder!!.showLoadFailed()
    }

    override fun showCategory(it: List<CategoryVo>) {
        it.get(0).isSelect = true
        showRightData(it.get(0).subCategorys)
        leftAdapter!!.addData(it)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateUserInfo(user: UserEvent) {
        Klog.e(contents = "frgm_category:updateUserInfo")
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }
}
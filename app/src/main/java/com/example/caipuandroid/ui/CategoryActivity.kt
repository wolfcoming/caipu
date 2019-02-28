package com.example.caipuandroid.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.caipuandroid.mvp.contract.CategoryContract
import com.example.caipuandroid.mvp.p.CategoryPresenter
import com.example.caipuandroid.ui.vo.CategoryVo
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : BaseMvpActivity<CategoryContract.Presenter>(), CategoryContract.View {
    override fun attachView(presenter: CategoryContract.Presenter) {
        presenter.attachView(this)
    }

    override fun getPresenter(): CategoryContract.Presenter {
        return CategoryPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        initView()
        mPresenter.getCategoryData()

    }


    var leftAdapter: RvLeftAdapter? = null
    var rightAdapter: RvLeftAdapter? = null
    private fun initView() {
        rvLeft.layoutManager = LinearLayoutManager(CategoryActivity@ this, LinearLayoutManager.VERTICAL, false)
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

        rvRight.layoutManager = GridLayoutManager(this, 3)
        rightAdapter = RvLeftAdapter()
        rvRight.adapter = rightAdapter
        rightAdapter!!.setOnItemClickListener { adapter, view, position ->
            val categoryVo = rightAdapter!!.getItem(position)!!
            toast(categoryVo.categoryName!!)
        }

    }


    fun showRightData(list: ArrayList<CategoryVo>) {
        rightAdapter!!.setNewData(list)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(message: String) {
        toast(message)
    }

    override fun showCategory(it: List<CategoryVo>) {
        it.get(0).isSelect = true
        showRightData(it.get(0).subCategorys)
        leftAdapter!!.addData(it)

    }


    class RvLeftAdapter : BaseQuickAdapter<CategoryVo, BaseViewHolder> {

        constructor() : super(R.layout.item_category_left)

        override fun convert(helper: BaseViewHolder?, item: CategoryVo?) {
            helper!!.setText(R.id.tvContent, item!!.categoryName)
            if (item.categoryLevel == 1) {
                if (item.isSelect) {
                    helper!!.setBackgroundColor(R.id.tvContent, Color.WHITE)
                    helper!!.setTextColor(R.id.tvContent, Color.RED)
                } else {
                    helper!!.setBackgroundColor(R.id.tvContent, Color.parseColor("#eeeeee"))
                    helper!!.setTextColor(R.id.tvContent, Color.BLACK)
                }
            }

        }
    }

}
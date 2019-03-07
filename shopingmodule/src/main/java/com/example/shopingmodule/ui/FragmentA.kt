package com.example.shopingmodule.ui

import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.base.BaseMvpFragment
import com.example.shopingmodule.R
import com.example.shopingmodule.mvp.SelectContract
import com.example.shopingmodule.mvp.SelectPresenter
import com.example.shopingmodule.ui.vo.SelectionVo
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentA : BaseMvpFragment<SelectPresenter>(), SelectContract.IView {
    override fun inflateView(): Any {
        return R.layout.fragment_a
    }

    var adapter: TempAdapter? = null
    override fun initView(anchor: View) {
        recycleview.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
        adapter = TempAdapter()
        recycleview.adapter = adapter
        mPresenter.getSelection("1")
    }

    override fun showSelection(datas: List<SelectionVo>) {
        adapter!!.setNewData(datas)
    }

    override fun getPresenter(): SelectPresenter {
        return SelectPresenter()
    }

    override fun attachView(presenter: SelectPresenter) {
        presenter.attachView(this)
    }

    inner class TempAdapter : BaseQuickAdapter<SelectionVo, BaseViewHolder> {
        override fun convert(helper: BaseViewHolder?, item: SelectionVo) {
            helper!!.setText(R.id.tv_price, item.price.toString())
                .setText(R.id.tv_brand, item.brand)
                .setText(R.id.tv_productDescription, item.productDescription)
                .setText(R.id.tv_favNum, item.favNum.toString())
            Glide.with(helper.itemView.context).load(Uri.parse(item.image)).into(helper.getView(R.id.iv_image))
        }

        constructor() : super(R.layout.item_handpicked_goods)
    }

}
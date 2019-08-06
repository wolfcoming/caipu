package com.example.caipuandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.caipuandroid.R
import com.example.caipuandroid.db.AppDatabase
import com.example.caipuandroid.db.CollectEntity
import com.example.caipuandroid.ui.adapter.CollectAdapter
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.view.muiltview.Gloading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragmen_collect.*

class CollectFragment : BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragmen_collect
    }

    var holder: Gloading.Holder? = null

    var subscribe: Disposable? = null
    var adapter: CollectAdapter? = null
    override fun initView(anchor: View) {
        holder = Gloading.getDefault().wrap(mRv).withRetry {
            getData()
        }
        mRv.layoutManager = LinearLayoutManager(context)
        adapter = CollectAdapter()
        mRv.adapter = adapter
        getData()
        adapter?.setOnItemClickListener  { adapter, view, position ->

            val entity = adapter.data[position] as CollectEntity
            val greens = Greens()
            entity.let {
                greens.name = it.name
                greens.brief = it.brief
                greens.burden = it.burden
                greens.collect = it.collect
                greens.id = it.id
                greens.img = it.img
                greens.makes = it.makes
                greens.tips = it.tips
                greens.views = it.views
                greens
            }
            val bundle = Bundle()
            bundle.putSerializable("Greens", greens)
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_DETAIL)
                .with(bundle)
                .navigation()
        }
    }

    private fun getData() {
        holder?.showLoading()
        subscribe = AppDatabase.getCollectDao().getAllCollectGreen()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if(it==null|| it.isEmpty()){
                    holder?.showEmpty()
                }else{
                    holder?.showLoadSuccess()
                    adapter?.setNewData(it)
                }

            }, {
                toast(it.message!!)
                holder?.showLoadFailed()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe.let {
            subscribe?.dispose()
        }
    }
}
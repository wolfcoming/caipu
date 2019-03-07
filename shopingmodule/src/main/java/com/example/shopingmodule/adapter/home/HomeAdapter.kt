package com.example.shopingmodule.adapter.home

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopingmodule.R


class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var datas: ArrayList<ContentBean> = ArrayList()


    override fun getItemViewType(position: Int): Int {
        return datas[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): RecyclerView.ViewHolder {

        if (viewtype == 1) {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_category, parent, false)
            return CategoryViewHolder(view)
        } else if (viewtype == 2) {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_goods, parent, false)
            return GoodsViewHolder(view)
        } else {
            throw Exception("message")
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1) {
            val categoryViewHolder = holder as CategoryViewHolder
            categoryViewHolder.recyclerView.layoutManager = GridLayoutManager(holder.recyclerView.context, 4)
            val adapter = CategoryAdapter()
            categoryViewHolder.recyclerView.adapter = adapter
            adapter.setNewData(datas[position].categorys)
        } else if (holder.itemViewType == 2) {
            val goodsViewHolder = holder as GoodsViewHolder
            goodsViewHolder.recyclerView.layoutManager = LinearLayoutManager(goodsViewHolder.recyclerView.context)
            val adapter = GoodsAdapter()
            goodsViewHolder.recyclerView.adapter = adapter
            adapter.setNewData(datas[position].goods)
        }
    }

    fun setNewDatas(data: ArrayList<ContentBean>) {
        this.datas.clear()
        this.datas = data
        notifyDataSetChanged()
    }


    inner class CategoryViewHolder : RecyclerView.ViewHolder {
        var recyclerView: RecyclerView

        constructor(itemView: View) : super(itemView) {
            recyclerView = itemView.findViewById(R.id.recycle)
        }
    }

    inner class GoodsViewHolder : RecyclerView.ViewHolder {
        var recyclerView: RecyclerView

        constructor(itemView: View) : super(itemView) {
            recyclerView = itemView.findViewById(R.id.recycle)
        }
    }

}
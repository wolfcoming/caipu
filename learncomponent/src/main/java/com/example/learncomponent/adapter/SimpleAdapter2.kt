package com.example.learncomponent.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.learncomponent.R

class SimpleAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder> {


    constructor(data: ArrayList<String>){
        this.datas = data
    }

    var datas: ArrayList<String> = ArrayList()



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(p0.context).inflate(R.layout.item_rv, null)
        return MyViewHolder(inflate)
    }


    override fun getItemCount(): Int {
        return this.datas.size;
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val myViewHolder = p0 as MyViewHolder
        myViewHolder.tv?.text = this.datas[p1]
    }



    inner class MyViewHolder : RecyclerView.ViewHolder {
        var tv: TextView? = null

        constructor(view: View) : super(view) {
            tv = view.findViewById(R.id.tvcontent)
        }


    }
}
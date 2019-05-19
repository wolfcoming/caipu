package com.example.caipuandroid.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.caipuandroid.R
import com.example.caipuandroid.remote.URLConfig
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.MakesBean
import kotlinx.android.synthetic.main.activity_goodsdetail.*
import org.w3c.dom.Text

class DetailBurdenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_NORMAL = 0//正常条目
    private val Show_Number = 3//正常条目
    private val TYPE_SEE_MORE = 1//查看更多
    private val TYPE_HIDE = 2//收起
    private var mOpen = false//是否是展开状态
    private var datas: List<BurdenBean>? = null

    constructor(list: List<BurdenBean>) {
        this.datas = list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        if (p1 == TYPE_NORMAL) {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.item_burden_detail, null)
            return MyViewHolder(view)
        } else if (p1 == TYPE_HIDE) {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.item_makes_text, null)
            return SimpleHolder(view, true)
        } else {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.item_makes_text, null)
            return SimpleHolder(view, false)
        }
    }

    override fun getItemCount(): Int {
        if (this.datas!!.size <= Show_Number) {
            return this.datas!!.size
        }

        if (mOpen) {
            return this.datas!!.size + 1
        } else {
            return Show_Number+1
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (datas?.size!! <= Show_Number) {
            return TYPE_NORMAL
        }
        if (mOpen) {
            if (position == datas?.size!!) {
                return TYPE_HIDE
            } else {
                return TYPE_NORMAL
            }

        } else {
            if (position == Show_Number) {
                return TYPE_SEE_MORE
            } else {
                return TYPE_NORMAL
            }

        }

    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is MyViewHolder) {
            val myViewHolder = p0 as MyViewHolder
            myViewHolder.tvName?.text = this.datas?.get(p1)?.name
            myViewHolder.tvDosage?.text = this.datas?.get(p1)?.dosage
        } else if (p0 is SimpleHolder) {
            val simpleHolder = p0
            simpleHolder.tvName?.text = if (simpleHolder.open) "收起" else "打开"
            simpleHolder.tvName?.setOnClickListener {
                if (simpleHolder.open) {
                    mOpen = false

                    notifyDataSetChanged()
                } else {
                    mOpen = true
                    notifyDataSetChanged()
                }
            }
        }
    }


    inner class MyViewHolder : RecyclerView.ViewHolder {
        var tvName: TextView? = null
        var tvDosage: TextView? = null

        constructor(itemView: View) : super(itemView) {
            tvName = itemView!!.findViewById<TextView>(R.id.tvName)
            tvDosage = itemView!!.findViewById<TextView>(R.id.tvDosage)
        }
    }


    inner class SimpleHolder : RecyclerView.ViewHolder {
        var tvName: TextView? = null
        var open: Boolean = false

        constructor(itemView: View, open: Boolean) : super(itemView) {
            this.open = open
            tvName = itemView!!.findViewById<TextView>(R.id.tvname)
        }
    }
}
package com.infoholdcity.baselibrary.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.infoholdcity.baselibrary.R

/**
 *@date     创建时间： 2018/11/2
 *@author   创建人：杨庆
 *@descript 描述：
 *@version
 */
class BaseToolBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var ibLeft: ImageButton? = null
    private var ibRight: ImageButton? = null
    private var tvTitle: TextView? = null

    init {
        val typeArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BaseToolBar, defStyleAttr, 0)
        val showAdd = typeArray.getBoolean(R.styleable.BaseToolBar_showadd, false)
        val showBack = typeArray.getBoolean(R.styleable.BaseToolBar_showback, true)
        val title = typeArray.getString(R.styleable.BaseToolBar_commontitle)

        val contentView: View = LayoutInflater.from(getContext()).inflate(R.layout.view_base_toolbar, null)
        ibLeft = contentView.findViewById<ImageButton>(R.id.ib_left)
        ibRight = contentView.findViewById<ImageButton>(R.id.ib_right)
        tvTitle = contentView.findViewById<TextView>(R.id.tv_title)
        addView(contentView)

        if (showAdd) {
            ibRight?.visibility = View.VISIBLE
        } else ibRight?.visibility = View.GONE


        if (showBack) {
            ibLeft?.setBackgroundResource(R.drawable.ic_back)
        } else ibLeft?.setBackgroundResource(R.drawable.ic_menu)

        if (title.isNotEmpty()) {
            tvTitle?.text = title
        }


        ibLeft?.setOnClickListener { v ->
            if (::leftListener.isInitialized)
                leftListener(v)
            else {
                if (context is Activity) {
                    val ac = context as Activity
                    ac.finish()
                }
            }
        }

        ibRight?.setOnClickListener { v ->
            if (::rightListener.isInitialized)
                rightListener(v)
        }
    }

    lateinit var leftListener: (view: View) -> Unit
    lateinit var rightListener: (view: View) -> Unit

    fun setLeftClickListener(e: (view: View) -> Unit) {
        this.leftListener = e
    }

    fun setRightClickListener(e: (view: View) -> Unit) {
        this.rightListener = e
    }

    fun setTitle(title: String) {
        tvTitle?.setText(title)
    }

}
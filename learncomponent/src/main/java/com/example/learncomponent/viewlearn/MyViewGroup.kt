package com.example.learncomponent.viewlearn

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.ViewGroup

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {



    init {
        setBackgroundColor(Color.YELLOW)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalHeight = 0
        var maxWidth = 0
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val layoutParams = view.layoutParams as MarginLayoutParams
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0)
            totalHeight += view.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            maxWidth = Math.max(maxWidth, view.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin)
        }
        setMeasuredDimension(
            maxWidth + paddingLeft + paddingRight,
            totalHeight + paddingBottom + paddingTop
        )

    }


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var totalTal = paddingTop
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val layoutParams = view.layoutParams as MarginLayoutParams
            val viewLeft = paddingLeft + layoutParams.leftMargin
            val viewTop = totalTal + layoutParams.topMargin
            view.layout(viewLeft, viewTop, viewLeft + view.measuredWidth, viewTop + view.measuredHeight)
            totalTal += view.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
        }


    }


}
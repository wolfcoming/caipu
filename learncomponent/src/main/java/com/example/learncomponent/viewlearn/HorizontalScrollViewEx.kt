package com.example.learncomponent.viewlearn

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import com.infoholdcity.basearchitecture.self_extends.Klog

class HorizontalScrollViewEx @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var mMeasureWidht = 0
        var mMeasureHeight = 0

        val modeWidht = MeasureSpec.getMode(widthMeasureSpec)
        val modeheight = MeasureSpec.getMode(heightMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (modeWidht == MeasureSpec.AT_MOST && modeheight == MeasureSpec.AT_MOST) {
            Klog.e(contents = getChildAt(0).measuredWidth.toString())
            mMeasureWidht = getChildAt(0).measuredWidth * childCount
            mMeasureHeight = getChildAt(0).measuredHeight
            setMeasuredDimension(mMeasureWidht, mMeasureHeight)
        } else if (modeWidht == MeasureSpec.AT_MOST) {
            mMeasureWidht = getChildAt(0).measuredWidth * childCount
            setMeasuredDimension(mMeasureWidht, heightMeasureSpec)
        } else if (modeheight == MeasureSpec.AT_MOST) {
            mMeasureHeight = getChildAt(0).measuredHeight
            setMeasuredDimension(widthMeasureSpec, mMeasureHeight)
        }


    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var chileLeft = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                val measuredWidth = childView.measuredWidth
                childView.layout(chileLeft, 0, chileLeft + measuredWidth, childView.measuredHeight)
                chileLeft += measuredWidth
            }

        }
    }


    private val mScroller by lazy { Scroller(context) }
    //最后滑动的坐标
    private var mLastX = 0
    private var mLastY = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x: Int = event.x.toInt()
        val y: Int = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }

            MotionEvent.ACTION_MOVE -> {

                var deltax = x - mLastX
                Klog.e(contents = "scrollX:${scrollX}  width:${measuredWidth}")
                var deltay = y - mLastY
                scrollBy(-deltax, 0)
                if (scrollX <= 0) {
                    scrollTo(0, 0)
                }
            }

            MotionEvent.ACTION_UP -> {

            }
        }
        mLastY = y
        mLastX = x

        return true


    }
    private var mLastInterceptX = 0
    private var mLastInterceptY = 0

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted:Boolean = false
        val x: Int = ev.x.toInt()
        val y: Int = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
            }

            MotionEvent.ACTION_MOVE -> {

                var deltaX = x - mLastInterceptX
                var deltaY = y - mLastInterceptY
                if(Math.abs(deltaX)> Math.abs(deltaY)){
                    intercepted = true
                }else{
                    intercepted = false
                }
            }

            MotionEvent.ACTION_UP -> {
                intercepted = false
            }
        }
        mLastY = y
        mLastX = x

        mLastInterceptX = x
        mLastInterceptY = y

        return intercepted
    }

}
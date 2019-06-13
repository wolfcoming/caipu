package com.example.learncomponent.nest

import android.content.Context
import android.support.v4.math.MathUtils
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Scroller
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast

class SuspendedLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), NestedScrollingParent2 {


    val nestedScrollingParentHelper by lazy { NestedScrollingParentHelper(this) }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Klog.e(contents = "onNestedPreScroll  type: " + type)
//        如果向上滑动 父容器先消费
        if (dy > 0) {
            val oldScrolly = scrollY
            scrollBy(0, dy)
            consumed[1] = scrollY - oldScrolly

        }
    }


    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Klog.e(contents = "onNestedScroll  type: " + type)
        if (dyUnconsumed < 0) {
            scrollBy(0, dyUnconsumed)
        }
    }


    override fun onStopNestedScroll(p0: View, p1: Int) {
        nestedScrollingParentHelper.onStopNestedScroll(p0, p1)
    }

    override fun onStartNestedScroll(p0: View, p1: View, p2: Int, p3: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(p0: View, p1: View, p2: Int, p3: Int) {
        nestedScrollingParentHelper.onNestedScrollAccepted(p0, p1, p2, p3)
    }


    override fun scrollTo(x: Int, y: Int) {
        val clamp = MathUtils.clamp(y, 0, headMeasureHeight)
        super.scrollTo(x, clamp)
    }


    var headMeasureHeight = 0

    /**
     * 重写测量方法 为整个父控件增加高度 （高度为 原来高度+第一个view的高度）
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount > 0) {
            val firstView = getChildAt(0)
//            先测量一下第一个view的测量高度
            measureChildWithMargins(firstView, widthMeasureSpec, 0, heightMeasureSpec, 0)
            headMeasureHeight = firstView.measuredHeight
//            重置父控件高度
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(heightMeasureSpec) + headMeasureHeight,
                    MeasureSpec.EXACTLY
                )
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        mVelocityTracker = VelocityTracker.obtain();
        mScroller = Scroller(context)


        getChildAt(0).setOnClickListener {
            toast("shit")
        }
    }

    var clickView: View? = null
    var downY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        //TODO 如何 只拦截不能滚动的子view事件 1. 根据x,y寻找点击view是否可滚动

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                clickView = findClickView(ev)
                downY = ev?.y
            }
            MotionEvent.ACTION_MOVE -> {
//                需要一定距离来
                var dexY = ev?.y - downY
                val TouchSlop = ViewConfiguration.get(context).scaledTouchSlop
                Klog.e(contents = "滑动距离 ${dexY} 系统最小滑动距离：${TouchSlop}")
                if(Math.abs(dexY)>TouchSlop){
                    if (clickView != null) {
                        if (clickViewCanScroll(clickView!!, ev)) {
                            Klog.e(contents = "滚动view")
                            return super.onInterceptTouchEvent(ev)
                        } else {
                            Klog.e(contents = "拦截事件")
                            return true
                        }
                    } else {
                        Klog.e(contents = "clickView == nil")
                    }
                }
            }
        }

        lastY = ev?.y!!
        return super.onInterceptTouchEvent(ev)
    }

    //        step 1: find touch view
    private fun findClickView(ev: MotionEvent): View? {
        var touchView: View? = null
        for (i in 0 until childCount) {
            val childView = getChildAt(i) ?: return null
            if (isTouchPointInView(childView, ev)) {
                touchView = childView
            }
        }
        return touchView
    }

    //        step 2: is touchview can scroll
    private fun clickViewCanScroll(touchView: View, ev: MotionEvent): Boolean {
        if (touchView !is ViewGroup) return false
        if (touchView != null) {
//            2.1 touchview 是否是滑动控件
            if (touchView is ScrollView || touchView is RecyclerView || touchView is AbsListView) {
                return true
            } else if (touchView is ViewGroup) {
//                2.2 寻找子view中是否有可滑动view
                for (k in 0 until touchView.childCount) {
                    return clickViewCanScroll(touchView.getChildAt(k), ev)
                }

            } else {
                return false
            }
        }
        return false
    }

    private fun isTouchPointInView(childView: View, ev: MotionEvent): Boolean {
        val location = IntArray(2)
        childView.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + childView.measuredWidth
        val bottom = top + childView.measuredHeight
        if (ev.x >= left && ev.x <= right && ev.y >= top && ev.y <= bottom) {
            return true
        }
        return false
    }

    var lastY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mVelocityTracker?.addMovement(event);
        var currentY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dexY = currentY - lastY
                scrollBy(0, (-dexY).toInt())
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker?.computeCurrentVelocity(
                    1000,
                    ViewConfiguration.get(context).getScaledMaximumFlingVelocity().toFloat()
                )
                val yVelocity = mVelocityTracker?.yVelocity
                mScroller?.fling(
                    0,
                    scrollY,
                    0,
                    (-yVelocity!!).toInt(),
                    -Int.MAX_VALUE,
                    Int.MAX_VALUE,
                    -Int.MAX_VALUE,
                    Int.MAX_VALUE
                )
                invalidate()
            }
        }
        lastY = currentY
        return true
    }


    override fun computeScroll() {

        Log.e("YYYY222", scrollY.toString())
        if (mScroller!!.computeScrollOffset()) {
            scrollTo(0, mScroller!!.currY)
            invalidate()
        }
        super.computeScroll()
    }

    override fun onDetachedFromWindow() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
        }
        super.onDetachedFromWindow()
    }


}
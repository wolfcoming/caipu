package com.example.caipuandroid.ui

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.LinearLayout
import java.lang.IllegalArgumentException
import android.support.v4.content.ContextCompat.getSystemService
import android.view.*
import android.widget.Scroller
import com.infoholdcity.basearchitecture.self_extends.log
import com.infoholdcity.basearchitecture.self_extends.toast
import java.time.Duration
import kotlin.math.abs


class SwipeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    var conentView: View? = null
    var menuView: View? = null
    val widhtPiex by lazy {
        val outMetrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getRealMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels
        widthPixels
    }
    val touchScrop by lazy { ViewConfiguration.get(context).getScaledTouchSlop() }
    override fun onFinishInflate() {
        super.onFinishInflate()
        mScroller = Scroller(context)
        setOnClickListener {
            toast("aaa")
        }
        if (childCount != 2) {
            throw IllegalArgumentException("务必使用两个child")
        }

        conentView = getChildAt(0)
        menuView = getChildAt(1)


        this.orientation = HORIZONTAL

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        for (i in 0 until childCount) {
            measureChild(getChildAt(i), 0, 0)
        }

        var w = widhtPiex + getChildAt(1).measuredWidth
        setMeasuredDimension(w, heightMeasureSpec)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        conentView?.layout(0, 0, widhtPiex, measuredHeight)
        menuView?.layout(widhtPiex, 0, widhtPiex + getChildAt(1).measuredWidth, measuredHeight)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        "子view的onInterceptTouchEvent方法".log()
        val vg = parent as ViewGroup
        vg!!.requestDisallowInterceptTouchEvent(true)
        return true
    }


    var downX: Float = 0f
    var downY: Float = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val vg = parent as ViewGroup
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                var dx = event.x - downX
                var dy = event.y - downY


                //移动

                if (Math.abs(scrollX) == 0)//一旦开始横向滑动就不要纵向滑动了
                    if (Math.abs(dy) > Math.abs(dx)) {

                        vg!!.requestDisallowInterceptTouchEvent(false)
                    } else {
                        vg!!.requestDisallowInterceptTouchEvent(true)
                    }

                //如果达到滑动认为的最小距离
                if (scrollX == 0) {
                    if (Math.abs(dx) > touchScrop) {
                        scrollBy((-dx).toInt(), 0)
                        ("执行了啊啊啊啊" + dx).log()
                    }
                } else {
                    scrollBy((-dx).toInt(), 0)
                    ("滚啊" + dx).log()
                }



                downY = event.y
                downX = event.x
            }

            MotionEvent.ACTION_UP -> {
                downY = event.y
                downX = event.x

                autoMove(800)


            }


        }

        return true
    }

    private fun autoMove(duration: Int) {
        if (menuView == null) return

        //缓慢滑动 会引起scrollBy方法不出现移动问题TODO
//        var startX = scrollX
//        var startY = y.toInt()
//        var endY = y.toInt()
//        var endX = 0
//        if (abs(scrollX) > menuView!!.measuredWidth / 2) {
//            "打开".log()
//            endX = menuView!!.measuredWidth
//        } else {
//            "关闭".log()
//            endX = 0
//        }
//        mScroller?.startScroll(startX, startY, endX - startX, endY - startY, duration)
//        invalidate()
        if (abs(scrollX) > menuView!!.measuredWidth / 2) {
            scrollTo(menuView!!.measuredWidth,y.toInt())
        }else{
            scrollTo(0,y.toInt())
        }

    }

    private var mScroller: Scroller? = null

//    override fun computeScroll() {
//        if (mScroller!!.computeScrollOffset()) {
//            scrollTo(mScroller!!.currX, mScroller!!.currY)
//            invalidate()
//        }
//        super.computeScroll()
//
//    }


    override fun scrollTo(x: Int, y: Int) {
        var xx = x
        if (xx < 0) {
            xx = 0
        }
        if (xx > menuView?.measuredWidth!!) {
            xx = menuView?.measuredWidth!!
        }
        super.scrollTo(xx, y)


    }


}
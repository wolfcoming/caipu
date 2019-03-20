package com.example.app.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import android.widget.Switch
import com.infoholdcity.basearchitecture.self_extends.Klog

class SlideSlipItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var childTwoWidth = 0
    val touchSlop by lazy { ViewConfiguration.get(context).getScaledTouchSlop() }
    val scroller = Scroller(context)

    init {
        Klog.e(contents = "init方法调用")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val childOne = getChildAt(0);
        val childTwo = getChildAt(1)
        //获取侧滑内容宽度
        childTwoWidth = childTwo.layoutParams.width
        Klog.e(contents = "onFinishInflate方法调用")
    }

    var downX = 0
    var downY = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x.toInt()
                downY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                var curX = event.x
                var curY = event.y
                var dexX = curX - downX
                var dexY = curY - downY
                if (Math.abs(dexX) > Math.abs(dexY)) {
                    if(Math.abs(dexX)>10){
                        Klog.e(contents = "requestDisallowInterceptTouchEvent")
                        parent.requestDisallowInterceptTouchEvent(true)
                        //判读是否还可以移动
                        var disX = scrollX - dexX
                        //处理快速滑动边界细节，
                        if (disX <= 0) {
                            disX = 0f
                        }
                        if (disX >= childTwoWidth) {
                            disX = childTwoWidth.toFloat()
                        }
                        //移动内容
                        this.scrollTo(disX.toInt(), 0)
                        downX = curX.toInt()
                        downY = curY.toInt()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                //手指抬起时看移动的距离，如果大于侧滑宽度的一半就自动打开，否则自动关闭
                if (Math.abs(scrollX) >= childTwoWidth / 2) {
                    val dex = childTwoWidth - scrollX
                    scroller.startScroll(scrollX, 0, dex, 0)
                    invalidate()

                } else {
                    val dex = 0 - scrollX
                    scroller.startScroll(scrollX, 0, dex, 0)
                    invalidate()

                }

            }
        }
        return true
    }


    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            invalidate()
        }
    }

    var downx2 = 0f
    var downy2 = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        //如果滑动距离大于系统认为的最小滑动距离 就拦截事件


        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downx2 = ev.x
                downy2 = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                var x = ev.x
                var y = ev.y
                var diff = Math.abs(x - downx2)
                downx2 = x
                downy2 = y
                if (Math.abs(downx2) > Math.abs(downy2) && diff > touchSlop) {
//                    拦截父容器的touch事件
                    Klog.e(contents = "拦截")
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


}
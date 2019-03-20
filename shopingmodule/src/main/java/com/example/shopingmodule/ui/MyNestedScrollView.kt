package com.example.shopingmodule.ui

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getHeight
import android.view.KeyEvent
import com.infoholdcity.basearchitecture.self_extends.Klog


/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2018/12/26 12:53 PM
 * desc   :
 */
class MyNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    var downX: Float = 0f
    var downY: Float = 0f
    private var isNeedScroll = true

    init {


    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        //思路：
//        如果没有滑动到底部 拦截掉向上拖动事件
//        如果滑动到了底部 不需要拦截 默认处理
        if (!isScrollBottom()) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    Klog.e(contents = "Down:$ev.y")
                    downX = ev.x
                    downY = ev.y
                }
                MotionEvent.ACTION_MOVE -> {
                    Klog.e(contents = "MOVe___:" + ev.y)
                    val curX = ev.x
                    val curY = ev.y

                    val dexX = curX - downX
                    val dexY = curY - downY
                    if (dexY < 0) {//向上移动
                        if (Math.abs(dexY) > Math.abs(dexX) && isNeedScroll) {//垂直方向移动
                            return true
                        }
                    }
                    downX = ev.x
                    downY = ev.y
                }
            }
        }else{

        }
        return super.onInterceptTouchEvent(ev)
    }

    fun isScrollBottom(): Boolean {
        val scrollY = this.getScrollY()
        val onlyChild = this.getChildAt(0)
        return onlyChild.getHeight() <= scrollY + this.getHeight()
    }

    /*
       该方法用来处理NestedScrollView是否拦截滑动事件  isNeedScroll= true 表示MyNestedScrollView消费该事件，false表示给子View消费
        */
    fun setNeedScroll(isNeedScroll: Boolean) {
        this.isNeedScroll = isNeedScroll
    }

}

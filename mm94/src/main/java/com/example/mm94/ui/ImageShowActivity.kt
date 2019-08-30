package com.example.mm94.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.mm94.R
import com.infoholdcity.basearchitecture.self_extends.log
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_imageshow.*

class ImageShowActivity : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        //不显示系统的标题栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_imageshow)
        getData()

    }

    companion object {
        fun startImageShowActivity(context: Context, imageUrl: String) {
            val intent = Intent(context, ImageShowActivity::class.java)
            intent.putExtra("imageUrl", imageUrl)
            context.startActivity(intent)
        }
    }

    private fun getData() {
        val imageurl = intent.getStringExtra("imageUrl")
        Glide.with(this).load(imageurl).into(photoView)
    }

    // <editor-fold defaultstate="collapsed" desc="处理手势关闭图片预览界面">


    var downX = 0f
    var downY = 0f
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        ("activity 在处理自己逻辑" + ev.action ).log()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                var dexX = ev.x - downX
                var dexY = ev.y - downY
                photoView.scrollBy((-dexX).toInt(), (-dexY).toInt())
                photoView.background.alpha = 180
                downY = ev.y
                downX = ev.x
            }
        }
        return super.onTouchEvent(ev)
    }


    /**
     * 解决多指抬起时 跳动问题
     */
    var downPointIndex = 0;
    /**
     * 重写事件分发
     *  在单指操作时 由自己处理 直接传递给onTouchEvent
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downPointIndex = ev.getPointerId(0)
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (ev.pointerCount == 1) {
                    val pointerId = ev.getPointerId(0)
                    if(pointerId == downPointIndex){
                        return onTouchEvent(ev)
                    }
                }
                "activity 不应该处理逻辑".log()
                downX = ev.x
                downY = ev.y
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    // </editor-fold>

}
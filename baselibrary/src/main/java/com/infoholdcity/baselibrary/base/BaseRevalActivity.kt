package com.infoholdcity.baselibrary.base

import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import com.infoholdcity.baselibrary.R

/**
 * Author：yangqing
 * Time：2018/12/11 16:48
 * Description： 前一个activity 需要 使用透明主题
 * <activity android:name=".beatuifyUi.MaterialSheetFabActivity"
android:theme="@style/ThemeTranslucent"/>




 */
open class BaseRevalActivity : BaseActiviy() {

    var duration: Long = 500//动画执行时长

    var mX: Int? = null
    var mY: Int? = null
    lateinit var viewGroup: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startRevealAnimator()

    }


    fun startRevealAnimator() {
        //获取上层activity传递过来的位置
        mX = intent.getIntExtra("x", 0)
        mY = intent.getIntExtra("y", 0)
//        mX = 200
//        mY = 300
        //activity的根布局
        viewGroup = findViewById<ViewGroup>(android.R.id.content)

        viewGroup.post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val animator = createRevealAnimator(false, viewGroup, mX!!, mY!!)
                animator?.addListener(animatorListener())
                animator?.start()
            }
        }
    }

    inner class animatorListener : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationFinish()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
            onAnimationStart()
        }

    }

    /**
     *  子类可以重写该方法，得知动画结束
     */
    open fun onAnimationFinish() {

    }

    /**
     *  子类可以重写该方法，得知动画开始
     */
    open fun onAnimationStart() {

    }

    override fun onBackPressed() {
        val animator = createRevealAnimator(true, viewGroup, mX!!, mY!!)
        animator?.duration = duration
        animator?.start()
        Handler().postDelayed(Runnable {
            viewGroup.alpha = 0f
            animator?.cancel()
            viewGroup.visibility = View.GONE
            finish()
            overridePendingTransition(R.anim.abc_fade_out, R.anim.abc_fade_in)
        }, duration)
    }

    // 动画
    private fun createRevealAnimator(reversed: Boolean, reversedView: View, x: Int, y: Int): Animator? {
        val hypot = Math.hypot(reversedView.getHeight().toDouble(), reversedView.getWidth().toDouble()).toFloat()
        val startRadius: Float = if (reversed) hypot else 0f
        val endRadius: Float = if (reversed) 0f else hypot

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val animator = ViewAnimationUtils.createCircularReveal(
                    reversedView, x, y,
                    startRadius,
                    endRadius)
            animator.duration = duration
            animator.interpolator = AccelerateDecelerateInterpolator()
//        if (reversed)
//            animator.addListener(animatorListener)
            return animator
        } else {
//            io.codetail.animation.ViewAnimationUtils 该类来实现兼容性 TODO
            return null
        }

    }
}
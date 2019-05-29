package com.example.learncomponent.viewlearn

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mColor: Int = Color.RED
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = mColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)



        val circlHeight = height - paddingTop - paddingBottom
        val circlWidth = width - paddingLeft - paddingRight
        val radius = Math.min(circlWidth,circlHeight) / 2

        canvas.save()
        canvas.drawCircle((paddingLeft+circlWidth/2).toFloat(), (paddingTop+circlHeight/2).toFloat(), radius.toFloat(), mPaint)
        canvas.restore()

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widhtMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)

        when {
            widhtMode == MeasureSpec.AT_MOST -> {
                widthSize =  600
                setMeasuredDimension(widthSize, heightMeasureSpec)
            }
            widhtMode == MeasureSpec.EXACTLY -> {
                setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
            }
        }

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


    }


}
package com.example.learncomponent.fresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.learncomponent.R;

import static com.example.learncomponent.fresh.SimpleRefreshState.*;

public class SimpleHeaderView extends BaseHeaderView {

    private ImageView ivArrow;
    private TextView tvContent;
    private ValueAnimator animator;
    private ValueAnimator loadingAnimator;

    public SimpleHeaderView(Context context) {
        this(context, null);
    }

    public SimpleHeaderView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public SimpleHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        animator = new ValueAnimator();
        loadingAnimator = new ValueAnimator();
        View view = LayoutInflater.from(context).inflate(R.layout.simplerefreshlayout_headview, this, false);
        ivArrow = view.findViewById(R.id.refresh_iv_arrow);
        tvContent = view.findViewById(R.id.fresh_tv_content);
        addView(view);
        changeStatus(STATUS_PullDownToRefresh);
    }



    /**
     * 更改刷新状态
     *
     * @param status
     */
    @Override
    public void changeStatus(int status) {
        if (status == STATUS_Refreshing) {
            animator.cancel();
            //更改成刷新加载图片
            ivArrow.setBackgroundResource(R.drawable.ic_refresh_loading);
            tvContent.setText(STATUS_RefreshingStr);
            loadingAnimal();
        } else if (status == STATUS_PullDownToRefresh || status == STATUS_ReleaseToRefresh) {
            if (loadingAnimator.isRunning()) {
                loadingAnimator.cancel();
            }
            ivArrow.setBackgroundResource(R.drawable.ic_refresh_arrow);
            if (status == STATUS_PullDownToRefresh) {
                tvContent.setText(STATUS_PullDownToRefreshStr);
                //旋转箭头向下
                roateIv(1);
            } else if (status == STATUS_ReleaseToRefresh) {
                tvContent.setText(STATUS_ReleaseToRefreshStr);
                //旋转箭头向上
                roateIv(0);
            }
        } else if (status == STATUS_RefreshFinish) {
            if (loadingAnimator.isRunning()) {
                loadingAnimator.cancel();
            }
            ivArrow.setRotation(0);
            ivArrow.setBackgroundResource(R.drawable.ic_refresh_finished);
            tvContent.setText(STATUS_RefreshFinishStr);
        }


    }


    /**
     * 旋转图片
     *
     * @param direction 0 代表箭头向上 1 代表箭头向下
     */
    private void roateIv(int direction) {
        if (direction == 0) {
            rotationAnimal(ivArrow.getRotation(), 180);
        } else {
            rotationAnimal(ivArrow.getRotation(), 0);
        }
    }

    /**
     * 动画旋转
     *
     * @param startValue 开始角度
     * @param destVale   结束角度
     */
    private void rotationAnimal(float startValue, float destVale) {

        if (animator.isRunning()) {
            animator.cancel();
        }
        animator.setDuration(100);
        animator.setFloatValues(startValue, destVale);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                ivArrow.setRotation(animatedValue);
            }
        });
        animator.start();
    }

    /**
     * 加载动画
     */
    private void loadingAnimal() {
        if (loadingAnimator.isRunning()) {
            loadingAnimator.cancel();
        }

        loadingAnimator.setDuration(800);
        loadingAnimator.setFloatValues(0, 360);
        loadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        loadingAnimator.setInterpolator(new LinearInterpolator());
        loadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ivArrow.setRotation((Float) animation.getAnimatedValue());
            }
        });
        loadingAnimator.start();


    }

}

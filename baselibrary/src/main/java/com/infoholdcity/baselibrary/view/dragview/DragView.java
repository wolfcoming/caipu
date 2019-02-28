package com.infoholdcity.baselibrary.view.dragview;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人：杨庆
 * @date 创建时间： 2018/12/28
 * @descript 描述：  可拖动view 三种状态，展开，隐藏，收起（固定到底部）
 */
public abstract class DragView extends LinearLayout implements DragViewInterface {


    /**
     * 记录对DragView 操作的最后一个位置
     */
    private Bound mLastBound = new Bound();
    /**
     * 记录当前DragView所在位置
     */
    private Bound mCurrentBound = new Bound();

    /**
     * 该view的活动范围
     */
    private Bound mRangeBound = new Bound();

    private ValueAnimator valueAnimator;
    private ValueAnimator scrollingAnimator;

    /**
     * 手动滑动 最后停留在底部的距离  单位dp
     */

    public abstract int getmStopBottom();

    /**
     * 拖动离开的时候  默认不做任何处理，如果需要 请重写改方法，并将返回值改为true
     *
     * @param yvel
     */
    public boolean onViewReleased(float yvel) {
        return false;
    }

    /**
     * 实时获取当前dragview 所在的位置
     *
     * @param mCurrentBound
     */
    public abstract void onViewDraging(Bound mCurrentBound);


    private List<View> canScrollViews = new ArrayList<>();

    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mRangeBound.left = 0;
        mRangeBound.right = getRight();
        mRangeBound.top = ((ViewGroup) getParent()).getHeight() - getMeasuredHeight();
        mRangeBound.bottom = mRangeBound.top + getMeasuredHeight();

        getCurrentBound();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        saveBound();
        canSrcollView();
    }

    public Bound getmRangeBound() {
        return mRangeBound;
    }

    /**
     * 获取其中可以滚动的view
     *
     * @return
     */
    private void canSrcollView() {
        canScrollViews.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ScrollView || child instanceof RecyclerView || child instanceof RecyclerView) {
                canScrollViews.add(child);
            } else if (child instanceof ViewGroup) {
                getScrollView((ViewGroup) child);
            }
        }

    }

    /**
     * 递归获取所有能滚动的view
     *
     * @param viewGroup
     */
    private void getScrollView(ViewGroup viewGroup) {
        for (int i1 = 0; i1 < viewGroup.getChildCount(); i1++) {
            View child = viewGroup.getChildAt(i1);
            if (child instanceof ScrollView || child instanceof ListView || child instanceof RecyclerView) {
                canScrollViews.add(child);
            } else if (child instanceof ViewGroup) {
                getScrollView((ViewGroup) child);
            }
        }
    }


    public List<View> getCanScrollViews() {
        return canScrollViews;
    }

    /**
     * 保存位置信息
     */
    private void saveBound() {
        mLastBound.left = getLeft();
        mLastBound.right = getRight();
        mLastBound.bottom = getBottom();
        mLastBound.top = getTop();

    }


    public Bound getCurrentBound() {
        mCurrentBound.left = getLeft();
        mCurrentBound.right = getRight();
        mCurrentBound.bottom = getBottom();
        mCurrentBound.top = getTop();
        onViewDraging(mCurrentBound);
        return mCurrentBound;
    }

    @Override
    public void showUpAt(int top) {

        if (top < mRangeBound.top) {
            return;
        }
        //动态运动到指定位置
        Bound destinationBound = new Bound();
        destinationBound.top = top;
        destinationBound.bottom = getHeight() + top;
        destinationBound.left = 0;
        destinationBound.right = getRight();
        startLayoutValueAnimal(mCurrentBound, destinationBound);

    }


    /**
     * 根据手指抬起瞬间的速率来实现惯性滚动
     *
     * @param velocity 负数为向上滑动，
     */
    public void scrollingByVelocity(float velocity) {
        if (velocity == 0) {
            return;
        }
        if (scrollingAnimator != null && scrollingAnimator.isRunning()) {
            scrollingAnimator.cancel();
        }
        scrollingAnimator = ValueAnimator.ofFloat(velocity / 100, 0)
                .setDuration((long) Math.abs(velocity / 10));
        scrollingAnimator.setInterpolator(new LinearInterpolator());
        scrollingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                int top = (int) (mCurrentBound.top + value);
                top = Math.min(mRangeBound.bottom - getmStopBottom(), Math.max(mRangeBound.top, top));
                int bottom = top + (mCurrentBound.bottom - mCurrentBound.top);
                layout(0, top, getRight(), bottom);
                getCurrentBound();
            }
        });
        scrollingAnimator.start();
    }

    /**
     * 动态的更改位置
     *
     * @param mCurrentBound
     * @param destinationBound
     */
    private void startLayoutValueAnimal(final Bound mCurrentBound, final Bound destinationBound) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofObject(new BoundEvaluator(), mCurrentBound, destinationBound);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Bound animatedValue = (Bound) animation.getAnimatedValue();
                DragView.this.layout(animatedValue.left, animatedValue.top, animatedValue.right, animatedValue.bottom);
                getCurrentBound();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mDragStateLinstener!=null){
                    if (mRangeBound.top == destinationBound.top) {
                        if (mDragStateLinstener != null) {
                            mDragStateLinstener.expand();
                        }
                    } else if (mRangeBound.bottom - getmStopBottom() == destinationBound.top) {
                        mDragStateLinstener.packUp();
                    } else if (destinationBound.top == ((DragLayout) getParent()).getHeight()) {
                        mDragStateLinstener.hide();
                    }
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();

    }

    @Override
    public void hinde() {
        DragLayout dragLayout = (DragLayout) getParent();
        Bound destinationBound = new Bound();
        destinationBound.top = dragLayout.getHeight();
        destinationBound.bottom = dragLayout.getHeight() + getHeight();
        destinationBound.left = 0;
        destinationBound.right = getRight();
        startLayoutValueAnimal(mCurrentBound, destinationBound);
    }

    @Override
    public void packUp() {
        int aa = mRangeBound.bottom - getmStopBottom();
        showUpAt(aa);
    }

    @Override
    public void expanded() {
        showUpAt(mRangeBound.top);
    }

    class BoundEvaluator implements TypeEvaluator<Bound> {

        Bound finalBound = new Bound();

        @Override
        public Bound evaluate(float fraction, Bound startValue, Bound endValue) {
            finalBound.left = startValue.left;
            finalBound.right = startValue.right;
            int height = startValue.bottom - startValue.top;
            finalBound.top = (int) (startValue.top + fraction * (endValue.top - startValue.top));
            finalBound.bottom = finalBound.top + height;
            return finalBound;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public interface DragStateLinstener {
        void hide();

        void packUp();

        void expand();
    }

    DragStateLinstener mDragStateLinstener;

    public void setDragStateLinstener(DragStateLinstener mDragStateLinstener) {
        this.mDragStateLinstener = mDragStateLinstener;
    }

}

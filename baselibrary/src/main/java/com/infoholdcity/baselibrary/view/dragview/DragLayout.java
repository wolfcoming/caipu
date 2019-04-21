package com.infoholdcity.baselibrary.view.dragview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.List;

/**
 * @author 创建人：杨庆
 * @date 创建时间： 2018/12/28
 * @descript 描述：
 */
public class DragLayout extends ViewGroup {
    private ViewDragHelper mViewDragHelper;
    private DragView mCurrentPressDragView;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                if (view instanceof DragView) {
                    mCurrentPressDragView = (DragView) view;
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (mCurrentPressDragView != null) {
                    top = Math.min(mCurrentPressDragView.getmRangeBound().bottom,
                            Math.max(mCurrentPressDragView.getmRangeBound().top, top));
                    return top;
                }
                return top;
            }


            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return 1;//大于0时才能在有焦点的vew上滑动   ？？？？
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (mCurrentPressDragView != null) {
                    mCurrentPressDragView.getCurrentBound();
                }
            }


            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mCurrentPressDragView != null) {
                    if(mCurrentPressDragView.onViewReleased(yvel)){
                        mCurrentPressDragView.scrollingByVelocity(yvel);
                    }
                }
            }
        });
    }

    float downX = 0, downY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //处理ViewDragHelper和可滑动事件冲突思路

        //需求：如果向上滑动   DragView 先滑动，滑动到顶部之后内部的RecycleView在滑动内容
//              如果向下滑动   内部recycleView先滑动 recycleView内容滑动完毕之后 dragView紧着滑动


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                向下滑动  先让RecycleView滑动 在滑动DragView
                if (ev.getY() - downY > 0) {
                    //如果是可以拖动的view （listView，ScrollerView，RecycleView） 则判断是否还可以内部滑动
                    boolean isCanDrag = true;
                    //如果还可以内部滑动，不拦截
                    if (mCurrentPressDragView != null) {
                        if (isCanScroll(mCurrentPressDragView, ev)) {
                            isCanDrag = false;
                        } else {
                            isCanDrag = true;
                        }
                    }
                    if (isCanDrag) {
                        return mViewDragHelper.shouldInterceptTouchEvent(ev);
                    } else {
                        return false;
                    }
                } else {//向上滑动，直接让DragView先滑动
//                    让ViewDragHelper帮忙判断 是否需要自己判断DragView是否已经滑动到顶部 合适拦截何时不拦截
                    return mViewDragHelper.shouldInterceptTouchEvent(ev);
                }
        }

        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;

    }

    /**
     * 根据 event 来获取点击的dragView中的子view是否还能继续拖动
     *
     * @param dragView
     * @param event
     * @return
     */
    public boolean isCanScroll(DragView dragView, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
//        获取点击的是dragView中的哪个View
        List<View> canScrollViews = dragView.getCanScrollViews();
        View clickView = null;
        for (int i = 0; i < canScrollViews.size(); i++) {
            if (isInInnerOfView(canScrollViews.get(i), x, y)) {
                clickView = canScrollViews.get(i);
            }
        }

        if (canChildScrollUp(clickView)) {
            return true;
        }

        return false;
    }

    /**
     * 点击坐标是否在view内部
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isInInnerOfView(View view, float x, float y) {
        Rect viewRect = new Rect();
        view.getGlobalVisibleRect(viewRect);
        if (x > viewRect.left && x < viewRect.right
                && y > viewRect.top && y < viewRect.bottom) {
            return true;
        }

        return false;
    }


    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp(View mDragListView) {
        if (mDragListView == null) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragListView, -1) || mDragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragListView, -1);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
//        直接设置父容器建议的宽高
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int left = getPaddingLeft();
            int top = getPaddingTop();
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            if (child instanceof DragView) {
                top = getHeight() - child.getMeasuredHeight();
                bottom = top + child.getMeasuredHeight();
            }

            child.layout(left, top, right, bottom);
        }


    }


}

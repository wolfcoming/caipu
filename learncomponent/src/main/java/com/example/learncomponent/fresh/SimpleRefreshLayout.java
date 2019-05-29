package com.example.learncomponent.fresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.infoholdcity.basearchitecture.self_extends.Klog;

public class SimpleRefreshLayout extends ViewGroup {

    private View contentView;
    private View headView;
    private View footerView;

    public SimpleRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount > 3) {
            throw new IllegalArgumentException("子布局数量不能超过三个");
        }

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
        }

        //获取中间内容控件的宽高作为刷新控件的宽高
        View contentView = getChildAt(1);
        MarginLayoutParams layoutParams = (MarginLayoutParams) contentView.getLayoutParams();

        setMeasuredDimension(contentView.getMeasuredWidth(), contentView.getMeasuredHeight());

    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        headView = getChildAt(0);
        contentView = getChildAt(1);
        footerView = getChildAt(2);

        headView.layout(l, t - headView.getMeasuredHeight(), r, t);
        contentView.layout(l, t, r, b);
        footerView.layout(l, b, r, b - footerView.getMeasuredHeight());


    }


    private boolean isScrollToTop() {
        if (contentView != null && headView != null) {
            if (contentView instanceof RecyclerView) {
                int scrollY = contentView.getScrollY();
                Klog.Companion.e("YYYY", scrollY + "");
            }
        }
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        isScrollToTop();
        return super.onInterceptTouchEvent(ev);
    }

    private void init() {

    }
}

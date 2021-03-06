package com.infoholdcity.baselibrary.view.freshview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import com.infoholdcity.basearchitecture.self_extends.Klog;

/**
 * @author yangqing
 * @time 2019/6/5 10:07 AM
 * @describe 刷新控件容器
 */
public class SimpleRefreshLayout extends ViewGroup {

    //<editor-fold desc="属性变量 property and variable">
    private View contentView;
    private BaseHeaderView headView;
    private BaseFooterView footerView;
    Scroller mScroller;

    float lastInterceptY = 0;
    //是否是触发顶部拦截
    boolean isTopIntercept = false;
    //    是否是触发底部拦截
    boolean isBottomIntercept = false;

    //最后滑动的坐标
    private float mLastY = 0;
    //    是否可以加载更多
    boolean canLoadMore = true;

    //是否可以下拉加载
    boolean canPullDownFresh = true;

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="构造函数">
    public SimpleRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="自动添加 header 和footer">
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount == 1) {
            contentView = getChildAt(0);
            if (headView != null) {
                removeView(headView);
            }
            headView = new SimpleHeaderView(getContext());
            addView(headView, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if (footerView != null) {
                removeView(footerView);
            }
            footerView = new SimpleFooterView(getContext());
            addView(footerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="测量和布局">
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            throw new IllegalArgumentException("子布局数量不能为0");
        }
        if (childCount > 3) {
            throw new IllegalArgumentException("子布局数量不能超过三个");
        }


        View contentView = null;
        if (childCount == 1) {
            contentView = getChildAt(0);
        } else if (childCount == 3) {
            contentView = getChildAt(1);
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
//            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            if (i == 1) {
                measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, 0);
            } else {
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            }
        }


        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


//        MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
        int resultWidth = 0;
        int resultHeight = 0;
        if (modeWidth == MeasureSpec.AT_MOST || modeWidth == MeasureSpec.UNSPECIFIED) {
            resultWidth = contentView.getMeasuredWidth();
        } else {
            resultWidth = sizeWidth;
        }

        if (modeHeight == MeasureSpec.AT_MOST || modeHeight == MeasureSpec.UNSPECIFIED) {
            resultHeight = contentView.getMeasuredHeight();
        } else {
            resultHeight = sizeHeight;
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measuredHeight = headView.getMeasuredHeight();
        Klog.Companion.e("YYYY", measuredHeight + "");
        headView.layout(l, t - headView.getMeasuredHeight(), r, t);


////        处理内容的布局 兼容父容器的padding 和 自身的margin
        MarginLayoutParams layoutParams = (MarginLayoutParams) contentView.getLayoutParams();
        int left = 0 + getPaddingLeft() + layoutParams.leftMargin;
        int top = 0 + getPaddingTop() + layoutParams.topMargin;
        int right = left + contentView.getMeasuredWidth();
        int bottom = top + contentView.getMeasuredHeight();
        contentView.layout(left, top, right, bottom);

        footerView.layout(l, b, r, b + footerView.getMeasuredHeight());

    }


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="事件拦截和处理">

    public static boolean canScrollUp(@NonNull View targetView) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final ViewGroup viewGroup = (ViewGroup) targetView;
                final AbsListView absListView = (AbsListView) targetView;
                return viewGroup.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0
                        || viewGroup.getChildAt(0).getTop() < targetView.getPaddingTop());
            } else {
                return targetView.getScrollY() > 0;
            }
        } else {
            return targetView.canScrollVertically(1);
        }
    }

    public static boolean canScrollDown(@NonNull View targetView) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final ViewGroup viewGroup = (ViewGroup) targetView;
                final AbsListView absListView = (AbsListView) targetView;
                final int childCount = viewGroup.getChildCount();
                return childCount > 0 && (absListView.getLastVisiblePosition() < childCount - 1
                        || viewGroup.getChildAt(childCount - 1).getBottom() > targetView.getPaddingBottom());
            } else {
                return targetView.getScrollY() < 0;
            }
        } else {
            return targetView.canScrollVertically(-1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        float y = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastInterceptY = ev.getY();
                return false;
            case MotionEvent.ACTION_MOVE:
                y = ev.getY();
//                MOVe 和 Down 第一次获取到的值是一样的 所以我们从第二次开始
                if (y == lastInterceptY) {
                    return false;
                }
                //向下滑动
                if (y - lastInterceptY > 0) {
                    if (!canPullDownFresh || mRefreshCallback == null) {
                        return false;
                    }
                    if (!canScrollDown(contentView)) {
                        isTopIntercept = true;
                        isBottomIntercept = false;
                        Klog.Companion.e("YYYY", "滚动到顶部了啊啊啊");
                        //直接返回true拦截时onTouchEvent事件是不会被触发的 所以在此记录手指滑动的上一刻位置
                        mLastY = y;
                        return true;
                    } else {
                        isTopIntercept = false;
                    }
                } else {

                    if (!canLoadMore || mLoadCallback == null) {
                        return false;
                    }

                    Klog.Companion.e("YYYY", "向sshang滑动：：：：yyy " + y + "   interceptY  " + lastInterceptY);
                    if (!canScrollUp(contentView)) {
                        isBottomIntercept = true;
                        isTopIntercept = false;
                        Klog.Companion.e("YYYY", "滚动到底部了啊啊啊");
                        //直接返回true拦截时onTouchEvent事件是不会被触发的 所以在此记录手指滑动的上一刻位置
                        mLastY = y;
                        return true;
                    } else {
                        isBottomIntercept = false;
                    }

                }
                lastInterceptY = y;
                break;

            case MotionEvent.ACTION_UP:
                Klog.Companion.e("YYYY", "ACTION_UP  ");
                break;

        }


        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        在加载中和刷新中的时候不去处理
        if (SimpleRefreshState.STATUS_Current == SimpleRefreshState.STATUS_Loading || SimpleRefreshState.STATUS_Current == SimpleRefreshState.STATUS_Refreshing) {
            return true;
        }

        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                onInterceptTouchEvent 在move过程中返回true 则会直接进入ACTION_MOVE条件 不会被触发Down
                Klog.Companion.e("YYYYY", "onTouchEvent 的down事件触发了");
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaY = y - mLastY;
                scrollBy(0, (int) -deltaY);
                //限制可拖动的范围
                limitRange();
                if (isTopIntercept) {
//                如果headview 全部显示 则更改刷新状态
                    if (Math.abs(getScrollY()) >= headView.getMeasuredHeight()) {
                        if (SimpleRefreshState.STATUS_Current != SimpleRefreshState.STATUS_ReleaseToRefresh) {
                            SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_ReleaseToRefresh;
                            headView.changeStatus(SimpleRefreshState.STATUS_Current);
                        }
                    } else {
                        if (SimpleRefreshState.STATUS_Current != SimpleRefreshState.STATUS_PullDownToRefresh) {
                            SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_PullDownToRefresh;
                            headView.changeStatus(SimpleRefreshState.STATUS_Current);
                        }
                    }

                } else if (isBottomIntercept) {
                    if (Math.abs(getScrollY()) >= footerView.getMeasuredHeight()) {
                        if (SimpleRefreshState.STATUS_Current != SimpleRefreshState.STATUS_ReleaseToLoad) {
                            SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_ReleaseToLoad;
                            footerView.changeStatus(SimpleRefreshState.STATUS_Current);
                        }
                    } else {
                        if (SimpleRefreshState.STATUS_Current != SimpleRefreshState.STATUS_PullUpToLoad) {
                            SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_PullUpToLoad;
                            footerView.changeStatus(SimpleRefreshState.STATUS_Current);
                        }

                    }
                }


                break;

            case MotionEvent.ACTION_UP:

                if (SimpleRefreshState.STATUS_Current == SimpleRefreshState.STATUS_PullDownToRefresh || SimpleRefreshState.STATUS_Current == SimpleRefreshState.STATUS_PullUpToLoad) {
                    //滚动到原来位置
                    smoothScrollTo(0, 0);
                } else if (SimpleRefreshState.STATUS_Current == SimpleRefreshState.STATUS_ReleaseToRefresh) {
//                    让刷新状态在顶部刷新
                    smoothScrollTo(0, -headView.getMeasuredHeight(), 100);
                    SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_Refreshing;
                    headView.changeStatus(SimpleRefreshState.STATUS_Current);
                    if (mRefreshCallback != null) {
                        mRefreshCallback.onRefresh(this);
                    }
                } else if (SimpleRefreshState.STATUS_Current == SimpleRefreshState.STATUS_ReleaseToLoad) {
                    SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_Loading;
                    footerView.changeStatus(SimpleRefreshState.STATUS_Current);
                    smoothScrollTo(0, footerView.getMeasuredHeight() + 20, 100);
                    if (mLoadCallback != null) {
                        mLoadCallback.onLoad(this);
                    }
                }
                break;

        }
        mLastY = y;
        return true;
    }

    /**
     * 限制滑动范围
     */
    private void limitRange() {
        if (isTopIntercept) {
            //滑动到顶部之后(header刚刚隐藏）就禁止继续滑动
            if (getScrollY() > 0) {
                scrollTo(0, 0);
            }
        }
        if (isBottomIntercept) {
            //滑动到底部之后(footer刚刚隐藏）就禁止继续滑动
            if (getScrollY() < 0) {
                scrollTo(0, 0);
            }
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="结束刷新和加载">

    /**
     * 刷新完成
     */
    public void freshFinished() {
        SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_RefreshFinish;
        if (headView != null) {
            headView.changeStatus(SimpleRefreshState.STATUS_Current);
        }
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
            }
        }, 400);

    }

    /**
     * 加载完成
     */
    public void loadFinished() {
        SimpleRefreshState.STATUS_Current = SimpleRefreshState.STATUS_LoadingFinish;
        if (headView != null) {
            footerView.changeStatus(SimpleRefreshState.STATUS_Current);
        }
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
            }
        }, 400);

    }

    /**
     * 是否可以加载更过
     *
     * @param b
     */
    public void setCanLoadMore(boolean b) {
        this.canLoadMore = b;
    }

    public void setCanPullDownFresh(boolean b) {
        this.canPullDownFresh = b;
    }


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="缓慢滑动">

    /**
     * 缓慢滑动到指定位置
     *
     * @param destX    目的x
     * @param destY    目的y
     * @param duration 使用的时间
     */
    private void smoothScrollTo(int destX, int destY, int duration) {
        //当前位置
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        //当前位置和目标位置的距离差
        int deltax = destX - scrollX;
        int deltay = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltax, deltay, duration);
        invalidate();
    }

    private void smoothScrollTo(int destX, int destY) {
        this.smoothScrollTo(destX, destY, 300);
    }

    private NestedScrollingParentHelper mNestedScrollingParentHelper;

    private void init(Context context) {
        mScroller = new Scroller(context);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="监听方法">

    private RefreshCallback mRefreshCallback;
    private LoadCallback mLoadCallback;

    public void setLoadCallbackListener(LoadCallback loadCallback) {
        this.mLoadCallback = loadCallback;
    }

    public void setRefreshCallbackListener(RefreshCallback refreshCallbackListener) {
        this.mRefreshCallback = refreshCallbackListener;
    }

    // </editor-fold>


//    @Override
//    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        Klog.Companion.e("YYYY__onNestedScroll", "dyUnconsumed: " + dyUnconsumed);
//
//    }
//
//
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        mScroller.fling(getScrollX(),getScrollY(),0, (int) velocityY,0,0,-Integer.MAX_VALUE, Integer.MAX_VALUE);
//        mScroller.computeScrollOffset();
//        invalidate();
//        return super.onNestedPreFling(target, velocityX, velocityY);
//    }
//
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(target, dx, dy, consumed);
//        Klog.Companion.e("YYYY__onNestedPreScroll", "dy: " + dy);
////        //如果 滚动到了底部  且启动了自动加载 则去显示
////        if(dy>0)
////        if (!canScrollUp(contentView)) {
////            smoothScrollTo(0, footerView.getMeasuredHeight() + 20, 100);
////        }
//    }
//
//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return true;
//    }
}

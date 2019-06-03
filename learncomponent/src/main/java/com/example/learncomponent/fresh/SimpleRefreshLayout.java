package com.example.learncomponent.fresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import com.infoholdcity.basearchitecture.self_extends.Klog;

import static com.example.learncomponent.fresh.SimpleRefreshState.*;

public class SimpleRefreshLayout extends ViewGroup {

    private View contentView;
    private SimpleHeaderView headView;
    private View footerView;


    Scroller mScroller;

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
        headView = (SimpleHeaderView) getChildAt(0);
        contentView = getChildAt(1);
        footerView = getChildAt(2);

        headView.layout(l, t - headView.getMeasuredHeight(), r, t);
        contentView.layout(l, t, r, b);
        footerView.layout(l, b, r, b + footerView.getMeasuredHeight());
        showLog(footerView.getMeasuredHeight());

    }

    private void showLog(Object log) {
        Klog.Companion.e("YYYYY", "result: " + log);
    }


    /**
     * 是否能向下或向上滑动
     *
     * @param direction >0 向上 <0 向下
     * @return
     */
    private boolean isCanScroll(int direction) {
        if (contentView != null && headView != null) {
            if (contentView instanceof RecyclerView) {
                boolean b = contentView.canScrollVertically(direction);
                return b;
            }
        }
        return false;
    }


    float lastInterceptY = 0;
    //是否是触发顶部拦截
    boolean isTopIntercept = false;
    //    是否是触发底部拦截
    boolean isBottomIntercept = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float y = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastInterceptY = ev.getY();
                Klog.Companion.e("YYYY", "触发down  " + lastInterceptY);
                return false;
            case MotionEvent.ACTION_MOVE:
                y = ev.getY();
                Klog.Companion.e("YYYY", "ACTION_MOVE  " + lastInterceptY);
//                MOVe 和 Down 第一次获取到的值是一样的 所以我们从第二次开始
                if (y == lastInterceptY) {
                    return false;
                }
                //向下滑动
                if (y - lastInterceptY > 0) {
                    Klog.Companion.e("YYYY", "向下滑动：：：：yyy " + y + "   interceptY  " + lastInterceptY);
                    if (!isCanScroll(-1)) {
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
                    Klog.Companion.e("YYYY", "向sshang滑动：：：：yyy " + y + "   interceptY  " + lastInterceptY);
                    if (!isCanScroll(1)) {
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


    //最后滑动的坐标
    private float mLastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

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
                        if (STATUS_Current != STATUS_ReleaseToRefresh) {
                            STATUS_Current = STATUS_ReleaseToRefresh;
                            headView.changeStatus(STATUS_Current);
                        }
                    } else {
                        if (STATUS_Current != STATUS_PullDownToRefresh) {
                            STATUS_Current = STATUS_PullDownToRefresh;
                            headView.changeStatus(STATUS_Current);
                        }
                    }

                } else if (isBottomIntercept) {
                    if (Math.abs(getScrollY()) >= footerView.getMeasuredHeight()) {
                        STATUS_Current = STATUS_ReleaseToLoad;
                    } else {
                        STATUS_Current = STATUS_PullUpToLoad;
                    }
                }


                break;

            case MotionEvent.ACTION_UP:

                if (STATUS_Current == STATUS_PullDownToRefresh || STATUS_Current == STATUS_PullUpToLoad) {
                    //滚动到原来位置
                    smoothScrollTo(0, 0);
                } else if (STATUS_Current == STATUS_ReleaseToRefresh) {
                    smoothScrollTo(0, -headView.getMeasuredHeight(), 100);
                    STATUS_Current = STATUS_Refreshing;
                    headView.changeStatus(STATUS_Current);
                    if (mRefreshCallback != null) {
                        mRefreshCallback.onRefresh(this);
                    }
                } else if (STATUS_Current == STATUS_ReleaseToLoad) {
                    STATUS_Current = STATUS_Loading;
                }
                break;

        }
        mLastY = y;
        return true;
    }

    /**
     * 刷新完成
     */
    public void freshFinished() {
        STATUS_Current = STATUS_RefreshFinish;
        if (headView != null) {
            headView.changeStatus(STATUS_Current);
        }
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
            }
        }, 400);

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


    private void init(Context context) {
        mScroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public RefreshCallback mRefreshCallback;

    public void setRefreshCallbackListener(RefreshCallback refreshCallbackListener) {
        this.mRefreshCallback = refreshCallbackListener;
    }


}

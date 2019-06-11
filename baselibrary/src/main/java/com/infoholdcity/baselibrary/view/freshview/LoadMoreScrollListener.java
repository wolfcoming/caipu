package com.infoholdcity.baselibrary.view.freshview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    //针对官方给出的三种LayoutManager的类型的标识
    private static final int TYPE_LINEAR_LAYOUT = 11;
    private static final int TYPE_GRID_LAYOUT = 12;
    private static final int TYPE_STAGGERED_GRID_LAYOUT = 13;

    private int layoutManagerType = -1;
    private int lastVisibleItemPosition;
    private int[] lastPositions;

    public abstract void loadMore();

    private boolean autoRefresh = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.e("YYYYQ", "onScrolled: ");


        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        /*
         * 其实GridLayoutManager就是继承自LinearLayoutManager，两类的处理可以合并成一起，
         * 但便于理解和可读性，进行了分开处理
         * */
        if (layoutManagerType == -1) {
            //记录下LayoutManager的类型
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = TYPE_GRID_LAYOUT;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = TYPE_LINEAR_LAYOUT;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = TYPE_STAGGERED_GRID_LAYOUT;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }
        //根据LayoutManager的类型找出显示的最后的位置
        switch (layoutManagerType) {
            case TYPE_LINEAR_LAYOUT:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case TYPE_GRID_LAYOUT:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case TYPE_STAGGERED_GRID_LAYOUT:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                //针对瀑布流布局，需要遍历每一列最后一个，寻找最后的位置
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

        //TODO 多次刷新后 不会触发该方法
        if (autoRefresh) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            //代表 没有布满一屏幕 触发加载
            if (itemCount - 1 == lastVisibleItemPosition) {
                loadMore();
                autoRefresh = false;
                Log.e("YYYYQ1", "onScrolled: " + autoRefresh);
            }
        }
    }

    public void autoRefreshFinish() {
        autoRefresh = true;
        Log.e("YYYYQ1", "autoRefreshFinish: " + autoRefresh);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) max = value;
        }
        return max;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        Log.e("YYYYQ", "onScrollStateChanged: ");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView滑动停止，若显示到最后的位置，则开始加载
        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && totalItemCount - 1 == lastVisibleItemPosition
                && visibleItemCount > 0 && autoRefresh) {
            loadMore();
            autoRefresh = false;
            Log.e("YYYYQ1", "onScrollStateChanged: " + autoRefresh);
        }
    }
}
package com.example.learncomponent.viewlearn;

import android.arch.persistence.room.Delete;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;

public class LoadMoreWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //数字大点，避免和被包装的adapter里的出现重复
    public static final int ITEM_TYPE_LOAD_FAILED_VIEW = Integer.MAX_VALUE - 1;
    public static final int ITEM_TYPE_NO_MORE_VIEW = Integer.MAX_VALUE - 2;
    public static final int ITEM_TYPE_LOAD_MORE_VIEW = Integer.MAX_VALUE - 3;
    public static final int ITEM_TYPE_NO_VIEW = Integer.MAX_VALUE - 4;//不展示footer view

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private RecyclerView.ViewHolder mLoadMoreHolder, mLoadFailedHolder, mLoadNoMoreHolder;

    private int mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
    private LoadMoreScrollListener mLoadMoreScrollListener;
    private boolean isHaveStatesView = true;
    private boolean isLoading = false;
    private boolean isLoadComplete = false;
    private int loadCompleCount = 0;//加载完成时的数据个数

    public LoadMoreWrapperAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mInnerAdapter = adapter;
        //创建滑动到底部的加载监听，在onAttachedToRecyclerView里给RecyclerView添加上
        mLoadMoreScrollListener = new LoadMoreScrollListener() {
            @Override
            public void loadMore() {
                if (mOnLoadListener != null) {
                    //可能进行了数据刷新
                    int itemCount = getItemCount();
                    if (itemCount < loadCompleCount) {
                        isLoadComplete = false;
                        loadMoreEnd();
                    }
                    if (!isLoading && !isLoadComplete) {
                        mOnLoadListener.onLoadMore();
                        isLoading = true;
                    }
                }
            }
        };
    }

    /**
     * 加载更多结束
     */
    public void loadMoreEnd() {
        mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
        isLoading = false;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }


    /**
     * 加载更多出错
     */
    public void loadError() {
        mCurrentItemType = ITEM_TYPE_LOAD_FAILED_VIEW;
        isHaveStatesView = true;
        isLoading = false;
        notifyItemChanged(getItemCount());
    }

    /**
     * 加载更多完毕，此后不会再次加载
     */
    public void loadMoreComplete() {
        mCurrentItemType = ITEM_TYPE_NO_MORE_VIEW;
        isHaveStatesView = true;
        isLoading = false;
        isLoadComplete = true;
        notifyItemChanged(getItemCount());
        loadCompleCount = getItemCount();
    }

    public void disableLoadMore() {
        mCurrentItemType = ITEM_TYPE_NO_VIEW;
        isHaveStatesView = false;
        notifyDataSetChanged();
    }

    public void setLoadStatusViewHolder(RecyclerView.ViewHolder loadMore, RecyclerView.ViewHolder loadFailed, RecyclerView.ViewHolder loadNoMore) {
        if (loadMore != null) mLoadMoreHolder = loadMore;
        if (loadFailed != null) mLoadFailedHolder = loadFailed;
        if (loadNoMore != null) mLoadNoMoreHolder = loadNoMore;
    }

    private RecyclerView.ViewHolder getLoadMoreViewHolder(Context context) {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = LoadStatusViewHolder.getDefaultHolder(context, "正在加载中");
        }
        return mLoadMoreHolder;
    }

    private RecyclerView.ViewHolder getLoadFailedViewHolder(Context context) {
        if (mLoadFailedHolder == null) {
            mLoadFailedHolder = LoadStatusViewHolder.getDefaultHolder(context, "加载出错,点击重试");
        }
        return mLoadFailedHolder;
    }

    private RecyclerView.ViewHolder getNoMoreViewHolder(Context context) {
        if (mLoadNoMoreHolder == null) {
            mLoadNoMoreHolder = LoadStatusViewHolder.getDefaultHolder(context, "——end——");
        }
        return mLoadNoMoreHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && isHaveStatesView) {
            return mCurrentItemType;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NO_MORE_VIEW) {
            return getNoMoreViewHolder(parent.getContext());
        } else if (viewType == ITEM_TYPE_LOAD_MORE_VIEW) {
            return getLoadMoreViewHolder(parent.getContext());
        } else if (viewType == ITEM_TYPE_LOAD_FAILED_VIEW) {
            return getLoadFailedViewHolder(parent.getContext());
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_LOAD_FAILED_VIEW
                && holder instanceof LoadStatusViewHolder) {
            ((LoadStatusViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadListener != null) {
                        mOnLoadListener.onRetry();
                    }
                }
            });
            return;
        }
        if (!isFooterType(holder.getItemViewType())) {
            mInnerAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        //注意 recyclerView setAdapter的时候就会回调这方法，要是setLayoutManager不在之前设置，这里getLayoutManager就为null
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //针对GridLayoutManager 的设置，让加载状态的View独占一行
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup oldSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == getItemCount() - 1 && isHaveStatesView) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (oldSizeLookup != null) {
                        return oldSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
        }
        recyclerView.addOnScrollListener(mLoadMoreScrollListener);
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (holder.getLayoutPosition() == getItemCount() - 1 && isHaveStatesView) {
            //针对StaggeredGridLayoutManager的设置，让加载状态的View独占一行
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (isHaveStatesView ? 1 : 0);
    }

    public boolean isFooterType(int type) {
        return type == ITEM_TYPE_NO_VIEW
                || type == ITEM_TYPE_LOAD_FAILED_VIEW
                || type == ITEM_TYPE_NO_MORE_VIEW
                || type == ITEM_TYPE_LOAD_MORE_VIEW;
    }


    public interface OnLoadListener {
        void onRetry();

        void onLoadMore();
    }

    private OnLoadListener mOnLoadListener;

    public LoadMoreWrapperAdapter setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
        return this;
    }

    /*
     *加载状态的ViewHolder的父类,需要自定义加载状态的viewHolder请继承该类
     *
     **/
    public static class LoadStatusViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public LoadStatusViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public static RecyclerView.ViewHolder getDefaultHolder(Context context, String text) {
            TextView view = new TextView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            view.setPadding(20, 20, 20, 20);
            view.setText(text);
            view.setGravity(Gravity.CENTER);
            return new LoadStatusViewHolder(view);
        }
    }
}
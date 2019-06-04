package com.example.learncomponent.fresh;

public class SimpleRefreshState {

    //    正常状态
    static int STATUS_None = 0;
    //    下拉去刷新状态
    static int STATUS_PullDownToRefresh = 1;
    static String STATUS_PullDownToRefreshStr = "继续下拉刷新";
    //    松开去刷新状态
    static int STATUS_ReleaseToRefresh = 2;
    static String STATUS_ReleaseToRefreshStr = "松开刷新";
    //    刷新中
    static int STATUS_Refreshing = 3;
    static String STATUS_RefreshingStr = "刷新中";

    static int STATUS_RefreshFinish = 4;
    static String STATUS_RefreshFinishStr = "刷新完成";

    //    上拉去加载状态
    static int STATUS_PullUpToLoad = 5;
    static String STATUS_PullUpToLoadStr = "继续上拉加载更多";
    //    松开去加载
    static int STATUS_ReleaseToLoad = 6;
    static String STATUS_ReleaseToLoadStr = "松开加载";
    //    加载中
    static int STATUS_Loading = 7;
    static String STATUS_LoadingdStr = "加载中";
    //    加载完成
    static int STATUS_LoadingFinish = 8;
    static String STATUS_LoadingFinishStr = "加载完成";

    //    当前状态
    static int STATUS_Current = STATUS_None;


}

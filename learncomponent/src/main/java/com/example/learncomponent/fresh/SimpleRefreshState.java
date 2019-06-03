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

    static int STATUS_RefreshFinish = 7;
    static String STATUS_RefreshFinishStr = "刷新完成";

    //    上拉去加载状态
    static int STATUS_PullUpToLoad = 4;
    //    松开去加载
    static int STATUS_ReleaseToLoad = 5;
    //    加载中
    static int STATUS_Loading = 6;
    //    当前状态
    static int STATUS_Current = STATUS_None;


}

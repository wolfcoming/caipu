package com.infoholdcity.baselibrary.view.dragview;

/**
 * @author 创建人：杨庆
 * @date 创建时间： 2018/12/28
 * @descript 描述：
 */
public interface DragViewInterface {
    /**
     * 在距离顶部 top 处展示
     *
     * @param top
     */
    void showUpAt(int top);

    /**
     * 隐藏dragView ，将dragView移动到父容器底部
     */
    void hinde();


    /**
     * 收起 （移动到底部固定的距离）
     */
    void packUp();

    /**
     * 展开 （完全显示）
     */
    void expanded();
}

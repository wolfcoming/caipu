package com.infoholdcity.baselibrary.view.addressSelectView;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.*;
import cn.com.infohold.smartcity.util.DeviceUtils;
import com.infoholdcity.baselibrary.R;

import static android.view.View.GONE;


public class BottomDialog3 extends Dialog {
    private SelectorNoDataProvider2 selector;

    public BottomDialog3(Context context) {
        super(context);
    }

    public BottomDialog3(Context context, int themeResId) {
        super(context, themeResId);
    }

    public BottomDialog3(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(Context context, SelectorNoDataProvider2 selector) {
        this.selector = selector;
        selector.getView().findViewById(R.id.selector_cancel).setVisibility(GONE);
        setContentView(selector.getView());
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        params.height = dip2px(context, 256);
        window.setAttributes(params);

//        window.setGravity(Gravity.BOTTOM);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnAddressSelectedListener(SelectedListener listener) {
        this.selector.setSelectedListener(listener);
    }


    public void setCancelListener(CancelListener cancelListener) {
        this.selector.setCancelListener(cancelListener);
    }

    public static BottomDialog3 show(Context context) {
        return show(context, null);
    }

    public static BottomDialog3 show(Context context, SelectedListener listener) {
        BottomDialog3 dialog = new BottomDialog3(context, R.style.bottom_dialog);
        dialog.selector.setSelectedListener(listener);
        dialog.show();
        return dialog;
    }

    /**
     * 显示在view的下方
     *
     * @param view
     */
    public void showAsDropDown(View view) {
        super.show();
        // 设置宽度为屏宽
        Window window = this.getWindow();
        //最重要的一句话，一定要加上！要不然怎么设置都不行！
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        //获取屏幕宽,
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.TOP;
        wlp.dimAmount = 0.00f;
        int[] location = new int[2];
        view.getLocationOnScreen(location); //获取在当前窗口内的绝对坐标

        String systemModel = DeviceUtils.getSystemModel();
        Log.d("WWW", "systemModel: " +systemModel);
        if (DeviceUtils.getSystemModel().contains("Redmi")) {
            wlp.y = location[1] - DeviceUtils.getStatusHeight(view.getContext());
        } else {
            wlp.y = location[1] + view.getHeight() - DeviceUtils.getStatusHeight(view.getContext());
        }
        window.setAttributes(wlp);

    }


    @Override
    public void show() {
        super.show();
        // 设置宽度为屏宽
        Window window = this.getWindow();
        //最重要的一句话，一定要加上！要不然怎么设置都不行！
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        //获取屏幕宽
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        wlp.dimAmount = 0.4f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(wlp);
    }

}
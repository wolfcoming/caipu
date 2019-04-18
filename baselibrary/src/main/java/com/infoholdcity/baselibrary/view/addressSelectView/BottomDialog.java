package com.infoholdcity.baselibrary.view.addressSelectView;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.*;
import com.infoholdcity.baselibrary.R;
import cn.com.infohold.smartcity.util.DeviceUtils;

import static android.view.View.GONE;


public class BottomDialog extends Dialog {
    private SelectorNoDataProvider selector;

    public BottomDialog(Context context) {
        super(context);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(Context context, SelectorNoDataProvider selector) {
        this.selector = selector;
        selector.getView().findViewById(R.id.selector_cancel).setVisibility(GONE);
        setContentView(selector.getView());
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
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

    public static BottomDialog show(Context context) {
        return show(context, null);
    }

    public static BottomDialog show(Context context, SelectedListener listener) {
        BottomDialog dialog = new BottomDialog(context, R.style.bottom_dialog);
        dialog.selector.setSelectedListener(listener);
        dialog.show();
        return dialog;
    }

    /**
     * 将对话框显示在控件view的下面
     * @param view
     */
    public void showBelowOfView(View view){
        super.show();
        // 设置宽度为屏宽
        Window window = this.getWindow();
        //最重要的一句话，一定要加上！要不然怎么设置都不行！
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        //获取屏幕宽
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.TOP;
        int[] location = new int[2];
        view.getLocationOnScreen(location); //获取在当前窗口内的绝对坐标

          //要减去状态栏的高度
        wlp.y = location[1] -  DeviceUtils.getStatusHeight(view.getContext());


        //限制对话框的高
//        if(wlp.height>256){
//            wlp.height = 256;
//        }
        window.setAttributes(wlp);




    }

}
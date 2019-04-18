package com.infoholdcity.baselibrary.view.addressSelectView;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.infoholdcity.baselibrary.R;

import static android.view.View.GONE;


public class BottomDialog2 extends Dialog {
    private Selector selector;

    public BottomDialog2(Context context) {
        super(context);
    }

    public BottomDialog2(Context context, int themeResId) {
        super(context, themeResId);
    }

    public BottomDialog2(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(Context context, Selector selector) {
        this.selector = selector;
        selector.getView().findViewById(R.id.selector_cancel).setVisibility(GONE);
        setContentView(selector.getView());
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = dip2px(context, 256);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(params);

//        window.setGravity(Gravity.BOTTOM);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnAddressSelectedListener(SelectedListener2 listener) {
        this.selector.setSelectedListener(listener);
    }


    public void setCancelListener(CancelListener cancelListener) {
        this.selector.setCancelListener(cancelListener);
    }

    public static BottomDialog2 show(Context context) {
        return show(context, null);
    }

    public static BottomDialog2 show(Context context, SelectedListener2 listener) {
        BottomDialog2 dialog = new BottomDialog2(context, R.style.bottom_dialog);
        dialog.selector.setSelectedListener(listener);
        dialog.show();
        return dialog;
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
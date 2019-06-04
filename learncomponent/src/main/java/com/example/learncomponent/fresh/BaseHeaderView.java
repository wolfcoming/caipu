package com.example.learncomponent.fresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseHeaderView extends LinearLayout {
    public BaseHeaderView(Context context) {
        super(context);
    }

    public BaseHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseHeaderView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void changeStatus(int status);
}

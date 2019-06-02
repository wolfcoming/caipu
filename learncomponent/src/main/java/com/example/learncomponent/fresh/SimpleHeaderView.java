package com.example.learncomponent.fresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.example.learncomponent.R;

public class SimpleHeaderView extends LinearLayout {
    public SimpleHeaderView(Context context) {
        this(context, null);
    }

    public SimpleHeaderView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public SimpleHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.simplerefreshlayout_headview,null);
    }


}

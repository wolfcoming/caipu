package com.infoholdcity.baselibrary.view.addressSelectView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.infoholdcity.baselibrary.R;


/**
 * Created by infohold on 2017/4/21.
 */

public class AddressSelectView extends LinearLayout {
    public AddressSelectView(Context context) {
        super(context);
    }

    public AddressSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public AddressSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private SelectorNoDataProvider selector;

    public void initView(SelectorNoDataProvider selector) {
        this.selector = selector;
        selector.getView().findViewById(R.id.selector_cancel).setVisibility(GONE);
        addView(selector.getView());
    }


    public void dissMiss() {
        if (AddressSelectView.this.getVisibility() == VISIBLE) {
            AddressSelectView.this.setVisibility(GONE);
        }
    }


}

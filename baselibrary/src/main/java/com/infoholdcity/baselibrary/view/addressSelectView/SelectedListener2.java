package com.infoholdcity.baselibrary.view.addressSelectView;

import java.util.ArrayList;


public interface SelectedListener2 {
    /**
     * 回调接口，根据选择深度，按顺序返回选择的对象。
     * */
    void onAddressSelected(ArrayList<ISelectAble2> selectAbles);
}

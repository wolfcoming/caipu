package com.infoholdcity.baselibrary.view.addressSelectView;

import java.util.List;


/**
 * Created by dun on 17/2/9.
 */

public interface DataProvider2 {
    void provideData(int currentId, String preId, DataReceiver receiver);


    interface DataReceiver {
        void send(List<ISelectAble2> data);
    }
}

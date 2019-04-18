package com.infoholdcity.baselibrary.view.addressSelectView;

import java.util.List;


/**
 * Created by dun on 17/2/9.
 */

public interface DataProvider {
    void provideData(int currentId, Area area, int preId, DataReceiver receiver);


    interface DataReceiver {
        void send(List<ISelectAble> data);
    }
}

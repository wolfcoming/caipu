package com.infoholdcity.baselibrary.view.addressSelectView;

import android.content.Context;
import android.view.View;
import com.infoholdcity.baselibrary.view.addressSelectView.i.CancelListener;
import com.infoholdcity.baselibrary.view.addressSelectView.i.DataProvider;
import com.infoholdcity.baselibrary.view.addressSelectView.i.ISelector;
import com.infoholdcity.baselibrary.view.addressSelectView.i.SelectedListener;

public class SelectorNoDataProvider implements ISelector {
    private Selector selector;
    private TreeData initTreeData;

    public SelectorNoDataProvider(Context context, final TreeData initTreeData, int deep) {
        selector = new Selector(context, initTreeData.getId(), deep);
        this.initTreeData = initTreeData;

        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentindex, String preId, DataReceiver receiver) {
                TreeData treeData = TreeData.getAreaById(initTreeData, preId);
                if (treeData != null) {
                    receiver.send(treeData.getSublistTreeData());
                } else {
                    receiver.send(null);
                }
            }
        });
    }





    @Override
    public View getView() {
        return selector.getView();
    }

    @Override
    public void setSelectedListener(SelectedListener listener) {
        this.selector.setSelectedListener(listener);
    }

    @Override
    public void setCancelListener(CancelListener cancelListener) {
        this.selector.setCancelListener(cancelListener);
    }


}

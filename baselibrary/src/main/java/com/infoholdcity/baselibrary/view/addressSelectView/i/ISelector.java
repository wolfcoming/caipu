package com.infoholdcity.baselibrary.view.addressSelectView.i;

import android.view.View;

public interface ISelector {
    void setSelectedListener(SelectedListener listener);
    void setCancelListener(CancelListener cancelListener);
    View getView();
}

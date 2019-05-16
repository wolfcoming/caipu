package com.example.learncomponent.proxy;

import android.os.Handler;
import android.os.Message;
import com.infoholdcity.basearchitecture.self_extends.Klog;


public class WoMen implements Subject {

    @Override
    public void shopping() {
        Klog.Companion.e("YYYY","女人喜欢买东西");
    }
}

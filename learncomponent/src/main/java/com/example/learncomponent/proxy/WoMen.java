package com.example.learncomponent.proxy;

import com.infoholdcity.basearchitecture.self_extends.Klog;

public class WoMen implements Subject {
    @Override
    public void shopping() {
        Klog.Companion.e("YYYY","女人喜欢买东西");
    }
}

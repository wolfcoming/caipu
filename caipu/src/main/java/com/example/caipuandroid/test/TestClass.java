package com.example.caipuandroid.test;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;

public class TestClass {

    public void main(){

    }

    class RvLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public RvLeftAdapter(int layoutResId) {
            super(layoutResId);
            HashMap hashMap = new HashMap();
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}

package com.example.caipuandroid.test;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class TestClass {

    public void main(){

    }

    class RvLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public RvLeftAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}

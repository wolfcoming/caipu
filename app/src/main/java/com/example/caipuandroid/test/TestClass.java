package com.example.caipuandroid.test;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.caipuandroid.remote.APIManage;
import com.example.caipuandroid.remote.bean.BaseBean;
import com.example.caipuandroid.remote.bean.CategoryBean;
import com.example.caipuandroid.ui.vo.CategoryVo;
import com.google.gson.Gson;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.List;

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

package com.example.learncomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.learncomponent.scrollview.InformationBean;
import com.example.learncomponent.scrollview.MarqueeInformationView;

import java.util.ArrayList;

public class ViewFilerActivity extends AppCompatActivity {


    private MarqueeInformationView marqueeInformationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnservice);
        marqueeInformationView = findViewById(R.id.marqueeview);
        initData();


    }




    ArrayList<InformationBean> beans = new ArrayList<InformationBean>();

    private void initData() {
        beans.clear();
        for (int i = 0; i < 3; i++) {
            InformationBean bean = new InformationBean();
            bean.setContent(i + "滚动的内滚动的内容滚动的内容滚动的内容滚动的内容滚动的内容滚动的内容容");
            bean.setDate("2018-11-21");
            if(i==0){
                bean.setFrom("大河报");
            }else if(i==1){
                bean.setFrom("金融网");
            }else {
                bean.setFrom("经济网");
            }
            beans.add(bean);
        }
        marqueeInformationView.addDatas(beans);
    }




}

package com.example.learncomponent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import com.example.learncomponent.fragment.FragmentA;
import com.example.learncomponent.fragment.FragmentB;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);


//        RecyclerView rv = (RecyclerView) findViewById(R.id.list);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new RecyclerView.Adapter() {
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View item = LayoutInflater.from(NestActivity.this)
//                        .inflate(R.layout.recyclerview_item, parent, false);
//                return new Holder(item);
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                Holder h = (Holder) holder;
//                h.textView.setText("item " + position);
//            }
//
//            @Override
//            public int getItemCount() {
//                return 100;
//            }
//
//            class Holder extends RecyclerView.ViewHolder {
//                TextView textView;
//
//                public Holder(View itemView) {
//                    super(itemView);
//                    textView = (TextView) itemView.findViewById(R.id.text);
//                }
//            }
//        });



    }






}

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
import android.view.ViewGroup;
import android.widget.TableLayout;
import com.example.learncomponent.fragment.FragmentA;
import com.example.learncomponent.fragment.FragmentB;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NestActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private List<String> titles;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);

        tableLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpage);

        //将各个标题装在titles里面
        titles = new ArrayList<String>();
        titles.add("第一个");
        titles.add("第二个");

        fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());


        MyFragmentPageAdapter myFragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPageAdapter);

        tableLayout.addTab(tableLayout.newTab().setText("第一个"),true);
        tableLayout.addTab(tableLayout.newTab().setText("第二个"),false);
        tableLayout.setupWithViewPager(viewPager);


        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                float height = getResources().getDisplayMetrics().heightPixels;
                ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
                layoutParams.height = (int) (height - tableLayout.getMeasuredHeight() - getStatusBarHeight());
                viewPager.setLayoutParams(layoutParams);


            }
        });

    }





    class MyFragmentPageAdapter extends FragmentPagerAdapter{

        public MyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }
}

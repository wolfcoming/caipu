package com.example.learncomponent.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.learncomponent.R;
import com.example.learncomponent.adapter.SimpleAdapter;
import com.example.learncomponent.fragment.FragmentA;

import java.util.ArrayList;
import java.util.List;

public class NestActivity extends AppCompatActivity {

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);

        fragments.add(new FragmentA());
        fragments.add(new FragmentA());
        fragments.add(new FragmentA());

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager vViewpager = findViewById(R.id.vViewpager);

        tabLayout.addTab(tabLayout.newTab(), false);
        tabLayout.addTab(tabLayout.newTab(), true);
        tabLayout.addTab(tabLayout.newTab(), false);

        tabLayout.setupWithViewPager(vViewpager, true);


        vViewpager.setOffscreenPageLimit(2);
        vViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                return position == 0 ? "AAA" : position == 1 ? "BBB" : "CCC";
            }
        });


    }


}

package com.example.learncomponent.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.learncomponent.R;
import com.example.learncomponent.SimpleAdapter;
import com.infoholdcity.baselibrary.base.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentA extends BaseFragment {

    @NotNull
    @Override
    protected Object inflateView() {
        return R.layout.fragment_fragmenta;
    }

    @Override
    protected void initView(@NotNull View anchor) {
        RecyclerView recycle = anchor.findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleAdapter simpleAdapter = new SimpleAdapter();
        recycle.setAdapter(simpleAdapter);
        List<String> datas = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            datas.add(i + "");
        }
        simpleAdapter.setNewData(datas);
        simpleAdapter.notifyDataSetChanged();

    }
}

package com.infoholdcity.baselibrary.view.addressSelectView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.infoholdcity.baselibrary.R;
import com.infoholdcity.baselibrary.view.addressSelectView.i.CancelListener;
import com.infoholdcity.baselibrary.view.addressSelectView.i.DataProvider;
import com.infoholdcity.baselibrary.view.addressSelectView.i.ISelector;
import com.infoholdcity.baselibrary.view.addressSelectView.i.SelectedListener;

import java.util.ArrayList;
import java.util.List;


public class Selector implements AdapterView.OnItemClickListener, ISelector {

    public static final int INDEX_INVALID = -1;
    private final Context context;
    private SelectedListener listener;
    private View view;
    private View indicator;
    private LinearLayout ll_tabLayout;
    private ProgressBar progressBar;

    private ListView listView;
    private TextView tvCancel;


    private int tabIndex = 0;//tab栏下标

    // 数据 第一层list是代表有多少层级，第二层list是代表每个层级的数据
    List<List<TreeData>> allDatas = new ArrayList<>();

    /* 每个tab的adapter  有多少层级就设置多少个adapter  多个adapter公用一个listview*/
    private SelectAdapter[] adapters;
    /*选择的深度*/
    //TODO 改成动态增加 优化内存
    private int selectDeep;
    private int[] selectedIndex;//每一层adapter 所选择的位置

    DataProvider dataProvider;

    /**
     * 该方法必须调用
     *
     * @param dataProvider
     */
    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
        getNextData(originPreId);
    }


    private String originPreId = "";//初始化过滤条件

    public Selector(Context context, String originPreId, int deep) {
        this.originPreId = originPreId;
        this.context = context;
        this.allDatas = new ArrayList<>(deep);
        selectedIndex = new int[deep];
        this.selectDeep = deep;
        for (int i = 0; i < deep; i++) {
            allDatas.add(new ArrayList<TreeData>());
        }
        initAdapters();
        initViews();
    }


    private void initAdapters() {
        adapters = new SelectAdapter[allDatas.size()];
        for (int i = 0; i < selectDeep; i++) {
            adapters[i] = new SelectAdapter(allDatas.get(i));
        }
    }

    private TextView[] tabs;

    private void initViews() {
        view = LayoutInflater.from(context).inflate(R.layout.address_selector, null);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        this.listView = (ListView) view.findViewById(R.id.listView);
        this.tvCancel = (TextView) view.findViewById(R.id.selector_cancel);
        this.indicator = view.findViewById(R.id.indicator);
        this.ll_tabLayout = (LinearLayout) view.findViewById(R.id.layout_tab);
        tabs = new TextView[allDatas.size()];
        for (int i = 0; i < allDatas.size(); i++) {
            //动态新增TextView
            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.simple_text_view, ll_tabLayout, false);
            ll_tabLayout.addView(textView);
            //绑定TextView的点击事件
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //设置tab 下标
                    tabIndex = finalI + 1;
                    //更新adapter
                    listView.setAdapter(adapters[finalI]);
                    //设置选择位置
                    if (selectedIndex[finalI] != INDEX_INVALID) {
                        listView.setSelection(selectedIndex[finalI]);
                    }
                    updateTabsVisibility(finalI);
                    updateIndicator(finalI);
                }
            });
            tabs[i] = textView;
        }


        this.listView.setOnItemClickListener(this);
        initListener();
        updateIndicator(tabIndex);
    }

    private void initListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();

                if (cancelListener != null) {
                    cancelListener.cancel();
                }

            }
        });
    }

    @Override
    public View getView() {
        return view;
    }


    /**
     * 指示器动画
     */
    private void updateIndicator(final int tabIndex) {
        view.post(new Runnable() {
            @Override
            public void run() {
                buildIndicatorAnimatorTowards(tabs[tabIndex]).start();
            }
        });
    }


    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }


    private void updateTabsVisibility(int index) {
        for (int i = 0; i < tabs.length; i++) {
            TextView tv = tabs[i];
            tv.setVisibility(allDatas.get(i).size() != 0 ? View.VISIBLE : View.GONE);
            tv.setEnabled(index != i);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex[tabIndex - 1] = position;


        TreeData selectAble = allDatas.get(tabIndex - 1).get(position);
        tabs[tabIndex - 1].setText(selectAble.getName());
        for (int i = tabIndex; i < this.allDatas.size(); i++) {
            tabs[i].setText("请选择");
            allDatas.get(i).clear();
            adapters[i].setSelectedIndex(-1);
            adapters[i].notifyDataSetChanged();
            this.selectedIndex[i] = INDEX_INVALID;
        }
        this.adapters[tabIndex - 1].setSelectedIndex(position);
        this.adapters[tabIndex - 1].notifyDataSetChanged();
        if (tabIndex == selectDeep) {
            callbackInternal(selectDeep);
            return;
        }

//        DeptAndUserbean deptAndUserbean = (DeptAndUserbean) selectItem;
        if (selectAble.getName().contains("全部")) {
            callbackInternal(tabIndex);
            return;
        }
        updateTabsVisibility(tabIndex - 1);
        updateIndicator(tabIndex);
        getNextData(selectAble.getId());
    }

    /**
     * 根据当前集合选择的id，向用户获取下一级子集的数据
     */
    private void getNextData(final String selectId) {
        if (dataProvider == null) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        dataProvider.provideData(tabIndex, selectId, new DataProvider.DataReceiver() {
            @Override
            public void send(List<TreeData> data) {
                if (data != null && data.size() > 0) {
                    //添加全部按钮

                    //更新当前tab下标
                    allDatas.get(tabIndex).clear();
                    allDatas.get(tabIndex).addAll(data);
                    adapters[tabIndex].notifyDataSetChanged();
                    listView.setAdapter(adapters[tabIndex]);
                } else {
                    //次级没有内容，直接回调
                    updateIndicator(tabIndex - 1);
                    updateProgressVisibility();
                    callbackInternal(tabIndex);
                    return;
                }

                updateTabsVisibility(tabIndex);
                updateProgressVisibility();
                updateIndicator(tabIndex);
                tabIndex = tabIndex + 1 >= selectDeep ? selectDeep : tabIndex + 1;
            }
        });
    }

    private void callbackInternal(int deep) {
        if (listener != null) {
//            selectDeep = deep;
            ArrayList<TreeData> result = new ArrayList<>(allDatas.size());
            for (int i = 0; i < deep; i++) {
                TreeData resultBean = allDatas.get(i) == null
                        || selectedIndex[i] == INDEX_INVALID ? null : allDatas.get(i).get(selectedIndex[i]);
                result.add(resultBean);
            }
            listener.onAddressSelected(result);
        }
    }

    private void updateProgressVisibility() {
        ListAdapter adapter = listView.getAdapter();
        int itemCount = adapter.getCount();
        progressBar.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
    }


    public SelectedListener getOnAddressSelectedListener() {
        return listener;
    }
    @Override
    public void setSelectedListener(SelectedListener listener) {
        this.listener = listener;
    }

    CancelListener cancelListener;

    @Override
    public void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;

    }


}

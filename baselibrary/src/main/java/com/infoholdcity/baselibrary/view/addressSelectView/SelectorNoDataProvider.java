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

import java.util.ArrayList;
import java.util.List;

public class SelectorNoDataProvider implements AdapterView.OnItemClickListener {

    public static final int INDEX_INVALID = -1;
    private final Context context;
    private SelectedListener listener;
    private View view;
    private View indicator;
    private LinearLayout ll_tabLayout;
    private ProgressBar progressBar;

    private ListView listView;
    private TextView tvCancel;
    private Area currenAre;//当前级别的数据
    private Area initArea;//初始数据；


    List<List<ISelectAble>> allDatas = new ArrayList<>();

    /* 每个tab的adapter */
    private SelectAdapter[] adapters;
    /*选择的深度*/
    private int selectDeep;

    private int tabIndex = 0;//文字指示器的索引
    private int[] selectedIndex;//记录每个list点击的位置


    public SelectorNoDataProvider(Context context, Area initArea, int deep) {
        this.initArea = initArea;
        this.context = context;

        this.allDatas = new ArrayList<>(deep);
        selectedIndex = new int[deep];
        this.selectDeep = deep;
        for (int i = 0; i < deep; i++) {
            allDatas.add(new ArrayList<ISelectAble>());
        }
        initAdapters();
        initViews();
        getNextData(0);
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
                if (cancelListener != null) {
                    cancelListener.cancel();
                }
            }
        });
    }

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
        //平移指示器
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        //动态改变指示器的宽度
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


    /**
     * 根据数据判断是否显示指示器文字
     *
     * @param index
     */
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

        ISelectAble selectAble = allDatas.get(tabIndex - 1).get(position);
        String name = selectAble.getName();
        if (name.contains("null")) {
            name = name.split("-")[1];
        }
        tabs[tabIndex - 1].setText(name);
        for (int i = tabIndex; i < this.allDatas.size(); i++) {
            tabs[i].setText("请选择");
            allDatas.get(i).clear();
            adapters[i].setSelectedIndex(-1);
            adapters[i].notifyDataSetChanged();
            this.selectedIndex[i] = INDEX_INVALID;
        }
        this.adapters[tabIndex - 1].setSelectedIndex(position);
        this.adapters[tabIndex - 1].notifyDataSetChanged();
        //将点击的数据保存起来,作为下一级的数据源
        currenAre = (Area) selectAble.getArg();
        if (tabIndex == selectDeep) {
            callbackInternal(selectDeep);
            return;
        }
        updateTabsVisibility(tabIndex - 1);
        updateIndicator(tabIndex);
        getNextData(selectAble.getId());
    }

    /**
     * 根据当前集合选择的id，向用户获取下一级子集的数据
     * 当前数据集合是树形结构 没有用到preid
     */
    private void getNextData(final int preId) {
        ArrayList<ISelectAble> addressData = getAddressData(currenAre);
        if (addressData.size() > 0) {
            allDatas.get(tabIndex).clear();
            allDatas.get(tabIndex).addAll(addressData);
            adapters[tabIndex].notifyDataSetChanged();
            listView.setAdapter(adapters[tabIndex]);

        } else {
            updateProgressVisibility();
            updateTabsVisibility(tabIndex - 1);
            updateIndicator(tabIndex - 1);
            //次级没有内容，直接回调
            callbackInternal(tabIndex);
            return;
        }

        updateTabsVisibility(tabIndex);
        updateProgressVisibility();
        updateIndicator(tabIndex);
        tabIndex = tabIndex + 1 >= selectDeep ? selectDeep : tabIndex + 1;

    }

    private void callbackInternal(int deep) {
        if (listener != null) {
            ArrayList<ISelectAble> result = new ArrayList<>(allDatas.size());
            for (int i = 0; i < deep; i++) {
                ISelectAble resultBean = allDatas.get(i) == null
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

    public void setSelectedListener(SelectedListener listener) {
        this.listener = listener;
    }

    /**
     * 获取地址选择器数据
     */
    public ArrayList<ISelectAble> getAddressData(Area areaSelect) {

        ArrayList<ISelectAble> addressData = new ArrayList<ISelectAble>();
        addressData.clear();
        Area area = null;
        if (areaSelect == null)//表示第一层级的地址
        {
            area = initArea;
        } else {
            area = areaSelect;
        }
        if (area != null) {
            List<Area> subArea = area.getSublistArea();
            //得到该级别的bgcode 向子级别添加全部按钮
            String parentBgCode = (String) area.getValue();
            String parentName = area.getName();
            if (subArea != null && subArea.size() > 0) {
                if (subArea.size() == 1) {//最后一级有的为“ ”过滤掉
                    if (subArea.get(0).getName().equals(" ")) {
                        return addressData;
                    } else {
                        setDataToAddressList(parentBgCode, parentName, subArea, addressData);
                    }
                } else {
                    setDataToAddressList(parentBgCode, parentName, subArea, addressData);
                }
            }
        }
        return addressData;
    }


    private void setDataToAddressList(String parentBgCode, String parentName, List<Area> subAreas, ArrayList<ISelectAble> addressList) {
        final ArrayList<Area> areas = new ArrayList<>();
        areas.clear();
        areas.addAll(subAreas);

        if(parentBgCode!=null){
            Area AllArea = new Area();
            AllArea.setName(parentName + "-全部");
            AllArea.setValue(parentBgCode);
//        AllArea.setBgCode(parentBgCode);
            areas.add(0, AllArea);
        }


        for (int i = 0; i < areas.size(); i++) {
            final Area a = areas.get(i);
            //向该目录下添加选取按钮
            final int finalI = i;
            addressList.add(new ISelectAble() {
                @Override
                public String getName() {
                    return areas.get(finalI).getName();
                }

                @Override
                public int getId() {
                    return finalI;
                }

                @Override
                public Object getArg() {
                    return a;
                }
            });
        }
    }

    CancelListener cancelListener;

    public void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }
}

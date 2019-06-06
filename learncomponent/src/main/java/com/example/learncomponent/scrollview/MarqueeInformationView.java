package com.example.learncomponent.scrollview;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.example.learncomponent.R;
import com.example.learncomponent.ViewFilerActivity;

import java.util.ArrayList;

/**
 * @author yangqing
 * @time 2019/6/5 11:37 AM
 * @describe 跑马灯资讯控件
 */
public class MarqueeInformationView extends LinearLayout {

    private ViewFlipper viewFlipper;

    public MarqueeInformationView(Context context) {
        this(context, null);
    }

    public MarqueeInformationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeInformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        创建父容器 （左边图片，右边滚动控件）
        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.HORIZONTAL);
        contentView.setGravity(Gravity.CENTER_VERTICAL);


//        创建资讯图片
        ImageView imageView = new ImageView(context);
        contentView.addView(imageView);
        imageView.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams ivParams = (LayoutParams) imageView.getLayoutParams();
        ivParams.height = Screen320Utils.px2dip(36);
        ivParams.width = ivParams.height;
        ivParams.leftMargin = Screen320Utils.px2dip(16);
        ivParams.rightMargin = Screen320Utils.px2dip(16);
        imageView.setLayoutParams(ivParams);

//        创建滚动控件
        viewFlipper = new ViewFlipper(context);
        Animation animationIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        viewFlipper.setInAnimation(animationIn);
        Animation animationOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_top);
        viewFlipper.setOutAnimation(animationOut);
        contentView.addView(viewFlipper);
        LinearLayout.LayoutParams vfParams = (LayoutParams) viewFlipper.getLayoutParams();
        vfParams.height = LayoutParams.WRAP_CONTENT;
        vfParams.width = LayoutParams.MATCH_PARENT;
        viewFlipper.setLayoutParams(vfParams);
        viewFlipper.setPadding(10,10,10,10);

        addView(contentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }


    /**
     * 添加数据
     * @param datas
     */
    public void addDatas(ArrayList<InformationBean> datas) {
        if(datas==null||datas.size()==0){
            return;
        }


        if(viewFlipper!=null&&viewFlipper.isFlipping()){
            viewFlipper.stopFlipping();
            viewFlipper.removeAllViews();
        }

        for (final InformationBean bean : datas) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_viewflipper, null);
            TextView tvContent = inflate.findViewById(R.id.tv_content);
            TextView tvDate = inflate.findViewById(R.id.tv_date);
            TextView tvFrom = inflate.findViewById(R.id.tv_from);
            ImageView imageView = inflate.findViewById(R.id.iv_img);
            tvContent.setText(bean.getContent());
            tvDate.setText(bean.getDate());
            tvFrom.setText(bean.getFrom());
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "哈哈哈"+bean.getFrom(), Toast.LENGTH_SHORT).show();
                }
            });
            viewFlipper.addView(inflate,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        viewFlipper.setFlipInterval(4000);
        viewFlipper.startFlipping();



    }


}

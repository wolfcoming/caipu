package com.infoholdcity.baselibrary.view.muiltview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.infoholdcity.baselibrary.R;
import com.infoholdcity.baselibrary.net.util.NetWorkHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

import static com.infoholdcity.baselibrary.view.muiltview.Gloading.*;

/**
 * simple loading status view for global usage
 *
 * @author billy.qi
 * @since 19/3/19 23:12
 */
@SuppressLint("ViewConstructor")
public class GlobalLoadingStatusView extends LinearLayout implements View.OnClickListener {

    private final TextView mTextView;
    private final Runnable mRetryTask;
    private final ImageView mImageView;
    private final ProgressBar mProgressbar;
    private Disposable subscribe;

    public GlobalLoadingStatusView(Context context, Runnable retryTask) {
        super(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true);
        mImageView = findViewById(R.id.image);
        mTextView = findViewById(R.id.text);
        mProgressbar = findViewById(R.id.progressbar);
        this.mRetryTask = retryTask;
        setBackgroundColor(Color.WHITE);

    }

    public void setMsgViewVisibility(boolean visible) {
        mTextView.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setStatus(int status) {
        boolean show = true;
        View.OnClickListener onClickListener = null;
        int image = R.drawable.icon_empty;
        int str = R.string.str_none;
        showLoading(false);
        switch (status) {
            case STATUS_LOAD_SUCCESS:
                show = false;
                break;
            case STATUS_LOADING:
                str = R.string.loading;
                showLoading(true);
                break;
            case STATUS_LOAD_FAILED:
                str = R.string.load_failed;
                Boolean networkConn = NetWorkHelper.INSTANCE.isNetConnected(getContext());
                if (networkConn != null && !networkConn) {
                    str = R.string.load_failed_no_network;
                }
                onClickListener = this;
                image = R.drawable.icon_failed;
                break;
            case STATUS_EMPTY_DATA:
                str = R.string.empty;
                image = R.drawable.icon_empty;
                break;
            default:
                break;
        }
        mImageView.setImageResource(image);
        setOnClickListener(onClickListener);
        mTextView.setText(str);
        setVisibility(show ? View.VISIBLE : View.GONE);
    }


    public void showLoading(boolean showLoading) {
        if (showLoading) {
            mProgressbar.setVisibility(VISIBLE);
            mImageView.setVisibility(GONE);
            //让文字动态
            subscribe = Observable.interval(600, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            changeLoadingText();
                        }
                    });

        } else {
            mProgressbar.setVisibility(GONE);
            mImageView.setVisibility(VISIBLE);
            if (subscribe != null) {
                subscribe.dispose();
            }
        }
    }


    private void changeLoadingText() {
        String trim = mTextView.getText().toString().trim();
        if (trim.equals("加载中")) {
            mTextView.setText("加载中.");
        } else if (trim.equals("加载中.")) {
            mTextView.setText("加载中..");
        } else if (trim.equals("加载中..")) {
            mTextView.setText("加载中...");
        } else if (trim.equals("加载中...")) {
            mTextView.setText("加载中");
        }
    }

    @Override
    public void onClick(View v) {
        if (mRetryTask != null) {

            mRetryTask.run();
        }
    }
}

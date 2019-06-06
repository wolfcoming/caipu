package com.example.learncomponent.scrollview;

import android.content.Context;
import android.view.WindowManager;

public class Screen320Utils {

    private static int screenWidth = -1;
    private static int screenHeight = -1;
    private static int statusBarHeight = -1;
    private static float scale320 = -1;
    private static float scale = -1;
    private static float fontScale = -1;


    /**
     * 根据手机的分辨率从px(像素)的单位转成为dp
     */
    public static int px2dip(float pxValue) {
        return (int) pxValue;
    }

}

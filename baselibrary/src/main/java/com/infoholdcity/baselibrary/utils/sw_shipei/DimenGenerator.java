package com.infoholdcity.baselibrary.utils.sw_shipei;

import java.io.File;


/**
 * java 代码运行自动生成sw文件
 */
public class DimenGenerator {

    /**
     * 设计稿尺寸(将自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 360;

    /**
     * 设计稿的高度  （将自己设计师的设计稿的高度填入）
     */
    private static final int DESIGN_HEIGHT = 640;

    public static void main(String[] args) {
        int smallest = DESIGN_WIDTH>DESIGN_HEIGHT? DESIGN_HEIGHT:DESIGN_WIDTH;  //     求得最小宽度
        DimenTypes[] values = DimenTypes.values();
        for (DimenTypes value : values) {
            File file = new File("");
            MakeUtils.makeAll(smallest, value, file.getAbsolutePath());
        }

    }

}

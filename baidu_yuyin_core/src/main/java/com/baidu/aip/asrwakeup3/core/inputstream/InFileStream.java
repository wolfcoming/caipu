package com.baidu.aip.asrwakeup3.core.inputstream;

import android.app.Activity;
import android.content.Context;
import com.baidu.aip.asrwakeup3.core.util.MyLogger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

/**
 * Created by fujiayi on 2017/6/20.
 */

public class InFileStream {

    private static SoftReference<Context> contextSoft;

    private static final String TAG = "InFileStream";

    public static void setContext(SoftReference<Context> contextSoftReference) {
        contextSoft = contextSoftReference;
    }

    private static String filename;

    private static InputStream is;

    public static void reset() {
        filename = null;
        is = null;
    }

    public static void setFileName(String filename) {
        InFileStream.filename = filename;
    }

    public static void setInputStream(InputStream is2) {
        is = is2;
    }

    public static InputStream create16kStream() {
        if (is != null) {
            return new FileAudioInputStream(is);
        } else if (filename != null) {
            try {
                return new FileAudioInputStream(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            return new FileAudioInputStream(createFileStream());
        }
        return null;
    }

    private static InputStream createFileStream() {
        try {
            if(contextSoft.get()==null) return null;
            Context context = contextSoft.get();
            InputStream is = context.getAssets().open("outfile.pcm");
            MyLogger.info(TAG, "create input stream ok " + is.available());
            return is;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
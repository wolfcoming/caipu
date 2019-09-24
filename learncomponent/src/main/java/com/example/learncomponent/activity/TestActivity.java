package com.example.learncomponent.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.learncomponent.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class TestActivity extends Activity {
    public static String author = "com.example.learncomponent.fileProvider";
    private static String savePath = "/sdcard/updateAPK/"; // apk保存到SD卡的路径
    private static String saveFileName = savePath + "apkName.apk"; // 完整路径名


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSavePath();


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveApk();
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(TestActivity.this, "挂载", Toast.LENGTH_SHORT).show();
                } else {
                    String state = Environment.getExternalStorageState();
                    Toast.makeText(TestActivity.this, "" + state, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveApk() {
        InputStream instream = null;
        OutputStream outputStream = null;
        try {

            File fileDir = new File(savePath);
            if (!fileDir.exists()) {
                boolean mkdir = fileDir.mkdir();
                Log.e("YYYY", "saveApk: mkdir: " + mkdir);
            }

            instream = getAssets().open("pro_4.1.4.apk");
            File apkFile = new File(saveFileName);

            if (!apkFile.exists()) {
                apkFile.createNewFile();
            }
            outputStream = new FileOutputStream(apkFile);

            if (instream != null) {
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = instream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
            }
            outputStream.flush();
            Log.e("YYYYYY", "saveApk: ");

            installApk();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private String getSavePath() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            savePath = getCacheDir().getAbsolutePath();
        }

        saveFileName = savePath + "/" + getPackageName()
                + ".apk";
        Log.e("YYYYYY", "getSavePath: " + saveFileName);
        return savePath;
    }

    public void installApk() {
        try {
            File apkFile = new File(saveFileName);
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, author, apkFile);
            installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(installIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("YYYYY", "installApk: " + e.getMessage());
        }

    }


}

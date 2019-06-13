package com.example.learncomponent;

import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;

import java.io.FileOutputStream;
import java.io.InputStream;

public class TTT {
    public void main() throws Exception{

        InputStream inputStream = null;
        FileOutputStream output = null;
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len= inputStream.read(buff))!=-1){
            output.write(buff,0,len);
        }

        String data = Environment.getExternalStorageDirectory()+"/tesseract/";
    }
}

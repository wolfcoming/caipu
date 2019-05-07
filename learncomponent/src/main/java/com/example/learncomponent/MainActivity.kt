package com.example.learncomponent

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore.Video.VideoColumns.LANGUAGE
import android.util.Log
import android.widget.Toast
import com.googlecode.tesseract.android.TessBaseAPI
import com.googlecode.tesseract.android.TessBaseAPI.OEM_CUBE_ONLY
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.TakePhotoBaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.devio.takephoto.model.TResult
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : TakePhotoBaseActivity() {
    val baseApi = TessBaseAPI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = NDKTools.getStringFromNDK()
        tvContent.text = text

        btnTest.setOnClickListener {
            initTessBaseApi()
            getTakePhoto().onPickFromCapture(getFileUri())
        }
    }

    fun initTessBaseApi(){
        try {

            val dataPath = Environment.getExternalStorageDirectory().toString()+"/tesseract/"
            val dir:File = File(dataPath+"tessdata/")
            if(!dir.exists()){
                dir.mkdirs()
                val input = resources.openRawResource(R.raw.eng)
                val file = File(dir,"eng.traineddata")
                val outPut = FileOutputStream(file)
                val buff = ByteArray(1024)
                var len = 0

                while (len!=-1){
                    if(len!=0){
                        outPut.write(buff, 0, len)
                    }
                    len = input.read(buff)
                }
                input.close()
                outPut.close()
            }
            val init = baseApi.init(dataPath, "eng")
            if(init){
                Klog.e(contents = "陈宫")
            }else{
                Klog.e(contents = "失败")
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    override fun takeSuccess(result: TResult?) {
        Klog.e(contents = result!!.image.originalPath)
        val bitmap = BitmapFactory.decodeFile(result.image.originalPath)
        imageview.setImageBitmap(bitmap)
        baseApi.setImage(bitmap)
        btnTest.text = baseApi.utF8Text
    }




}

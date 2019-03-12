package com.example.caipuandroid.ui

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.caipuandroid.R
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.base.TakePhotoBaseActivity
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.activity_goodadd.*
import com.qiniu.android.common.FixedZone
import com.qiniu.android.http.CompletionHandler
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.*
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.TResult
import org.devio.takephoto.model.TakePhotoOptions
import org.json.JSONObject
import java.io.File
import java.time.temporal.TemporalQueries.zone


@Route(path = ARouterConfig.ACT_CAIPU_ADD)
class GoodsAddActivity : TakePhotoBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goodadd)
        initQiniu()
        btnAddImg.setOnClickListener {
            //            uploadManager!!

            val imageUri = getFileUri()

            val takePhotoOptions = TakePhotoOptions.Builder()
                .setCorrectImage(true)//是否纠正旋转
                .create()
            getTakePhoto().setTakePhotoOptions(takePhotoOptions)
            val config = CompressConfig.Builder()
                .enablePixelCompress(true)//是否开启像素压缩
                .enableQualityCompress(true)//质量压缩
                .enableReserveRaw(true)//是否保留原件
                .create()
            getTakePhoto()!!.onEnableCompress(config, false)//压缩后的文件存放在了cache缓存文件夹
            getTakePhoto().onPickFromCapture(imageUri)
        }
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)

        val filePath = result!!.image.compressPath
        val key = System.currentTimeMillis().toString() + ".jpg"
        val token =
            "9SGCOiw4SnXTZj0QumvO7QMsAKdGiherr_DnHXpg:bJunwWScZoMvdGpTYVuO1DuziNY=:eyJzY29wZSI6ImRqYW5nb19jYWlwdV9pbWFnZSIsImRlYWRsaW5lIjoxNTUyMjIyNjU1fQ==";

        uploadManager!!.put(filePath, key, token, object : UpCompletionHandler {
            override fun complete(key: String?, info: ResponseInfo, response: JSONObject) {
                if (info.isOK()) {
                    Log.i("qiniu", "Upload Success");
                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
                Log.i("qiniu___", "$key,\r\n $info,\r\n $response");
            }

        }, UploadOptions(null, null, false, object : UpProgressHandler {
            override fun progress(key: String?, percent: Double) {

                Klog.e(contents = "$key:::::$percent")
            }

        }, null))
    }


    override fun takeCancel() {
        super.takeCancel()
    }


    override fun takeFail(result: TResult?, msg: String?) {
        super.takeFail(result, msg)
    }

    var uploadManager: UploadManager? = null

    private fun initQiniu() {

        val config = Configuration.Builder()
            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
            .connectTimeout(10)           // 链接超时。默认10秒
            .useHttps(true)               // 是否使用https上传域名
            .responseTimeout(60)          // 服务器响应超时。默认60秒
//            .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//            .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
            .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
            .build()
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = UploadManager(config)
    }


    fun getFileUri(): Uri {
        val file = File(Environment.getExternalStorageDirectory(), "/111/" + System.currentTimeMillis() + ".jpg")
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        val imageUri = Uri.fromFile(file)
        return imageUri
    }

}
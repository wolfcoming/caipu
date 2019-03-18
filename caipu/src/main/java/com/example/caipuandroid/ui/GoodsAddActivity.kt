package com.example.caipuandroid.ui

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.caipuandroid.R
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.caipuandroid.mvp.contract.AddGreensContract
import com.example.caipuandroid.mvp.p.AddGreensPresenter
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.BurdenAdapter
import com.example.caipuandroid.ui.adapter.MakesAdapter
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.Greens
import com.example.caipuandroid.ui.vo.MakesBean
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
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
import java.lang.Exception
import java.time.temporal.TemporalQueries.zone


@Route(path = ARouterConfig.ACT_CAIPU_ADD)
class GoodsAddActivity : BaseMvpActivity<AddGreensContract.Presenter>(), AddGreensContract.View {
    override fun getPresenter(): AddGreensContract.Presenter {
        return AddGreensPresenter()
    }

    override fun attachView(presenter: AddGreensContract.Presenter) {
        presenter.attachView(this)
    }


    var imgTag: String = ""
    var stepView: ImageView? = null
    var mPostion: Int = 0//步骤图片

    val makesSteps: ArrayList<MakesBean> = ArrayList()

    val service by lazy { IServiceNetImpl() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goodadd)
        //封面图
        ivCover.setOnClickListener {
            imgTag = "ivCover"
            getTakePhoto().onPickFromCapture(getFileUri())
        }
//        所需材料
        rvBundls.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val burdenAdapter = BurdenAdapter()
        rvBundls.adapter = burdenAdapter
        val datas: ArrayList<BurdenBean> = ArrayList()
        for (i in 0 until 1) {
            val burdenBean = BurdenBean("", "")
            datas.add(burdenBean)
        }
        burdenAdapter.setNewData(datas)
        llAddBurdenView.setOnClickListener {
            val burdenBean = BurdenBean("", "")
            burdenAdapter.addData(burdenBean)
        }

        //步骤
        rvMakes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val makesAdapter = MakesAdapter()
        rvMakes.adapter = makesAdapter
        for (i in 0 until 3) {
            val makesBean = MakesBean("", "")
            makesSteps.add(makesBean)
        }
        makesAdapter.setNewData(makesSteps)
        llAddMakesView.setOnClickListener {
            val makesBean = MakesBean("", "")
            makesAdapter.addData(makesBean)
        }

        makesAdapter.imageClick = object : MakesAdapter.IImageClick {
            override fun imageClick(imageView: ImageView, position: Int) {
                imgTag = "ivStepImg"
                stepView = imageView
                mPostion = position
                getTakePhoto().onPickFromCapture(getFileUri())
            }
        }

        btnUpload.setOnClickListener {
            burdenAdapter.notifyDataSetChanged()
            datas.forEach { bean ->
                Klog.e(contents = bean.name)
            }


            makesSteps.forEach { makesBean ->
                Klog.e(contents = makesBean.step + "---" + makesBean.stepImg)

            }
        }
    }

    override fun takeSuccess(result: TResult?) {
        if (imgTag.equals("ivStepImg")) {
            try {
                if (stepView != null) {
                    makesSteps[mPostion].stepImg = result!!.image.originalPath
                    Glide.with(this).load(result!!.image.originalPath).into(stepView!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else
            Glide.with(this).load(result!!.image.compressPath).into(ivCover)
    }

    var uploadManager: UploadManager? = null


//    private fun initQiniu() {
//
//        val config = Configuration.Builder()
//            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
//            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
//            .connectTimeout(10)           // 链接超时。默认10秒
//            .useHttps(true)               // 是否使用https上传域名
//            .responseTimeout(60)          // 服务器响应超时。默认60秒
////            .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
////            .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//            .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
//            .build()
//// 重用uploadManager。一般地，只需要创建一个uploadManager对象
//        uploadManager = UploadManager(config)
//
//
//        /**
//         *   initQiniu()
//        btnAddImg.setOnClickListener {
//        val imageUri = getFileUri()
//        val takePhotoOptions = TakePhotoOptions.Builder()
//        .setCorrectImage(true)//是否纠正旋转
//        .create()
//        getTakePhoto().setTakePhotoOptions(takePhotoOptions)
//        val config = CompressConfig.Builder()
//        .enablePixelCompress(true)//是否开启像素压缩
//        .enableQualityCompress(true)//质量压缩
//        .enableReserveRaw(true)//是否保留原件
//        .create()
//        getTakePhoto()!!.onEnableCompress(config, false)//压缩后的文件存放在了cache缓存文件夹
//        getTakePhoto().onPickFromCapture(imageUri)
//        }
//        btnAddGreens.setOnClickListener {
//        val greens = Greens()
//        greens.name = "name"
//        greens.brief = "breief"
//        greens.views = 10
//        greens.collect = 10
//        greens.tips = "tip"
//        greens.makes = "makes"
//        greens.burden = "makes"
//        greens.img = "img"
//        service.addGreens(greens)
//        .excute()
//        .subscribe({ it ->
//        if (it) {
//        toast("上传成功")
//        } else {
//        toast("上传失败")
//        }
//
//        }, {
//        toast(it.message!!)
//        })
//        }
//         */
//    }
//
//


//    val filePath = result!!.image.compressPath
//    val key = System.currentTimeMillis().toString() + ".jpg"
//    val token =
//        "9SGCOiw4SnXTZj0QumvO7QMsAKdGiherr_DnHXpg:LYk-YYmlkK6HVJD0uXZJfKmbQwI=:eyJzY29wZSI6ImRqYW5nb19jYWlwdV9pbWFnZSIsImRlYWRsaW5lIjoxNTUyMzc4NjkyfQ==";
//
//
//    uploadManager!!.put(filePath, key, token, object : UpCompletionHandler {
//        override fun complete(key: String?, info: ResponseInfo, response: JSONObject) {
//            if (info.isOK()) {
//                Log.i("qiniu", "Upload Success");
//                toast("图片上传到七牛")
//            } else {
//                Log.i("qiniu", "Upload Fail");
//                toast("上传失败")
//                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
//            }
//            Log.i("qiniu___", "$key,\r\n $info,\r\n $response");
//        }
//
//    }, UploadOptions(null, null, false, object : UpProgressHandler {
//        override fun progress(key: String?, percent: Double) {
//
//            Klog.e(contents = "$key:::::$percent")
//        }
//
//    }, null))


}
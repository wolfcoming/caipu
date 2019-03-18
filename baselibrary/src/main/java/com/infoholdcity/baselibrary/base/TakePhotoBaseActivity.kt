package com.infoholdcity.baselibrary.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.infoholdcity.baselibrary.base.BaseActiviy
import org.devio.takephoto.app.TakePhoto
import org.devio.takephoto.app.TakePhotoActivity
import org.devio.takephoto.app.TakePhotoImpl
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.InvokeParam
import org.devio.takephoto.model.TContextWrap
import org.devio.takephoto.model.TResult
import org.devio.takephoto.model.TakePhotoOptions
import org.devio.takephoto.permission.InvokeListener
import org.devio.takephoto.permission.PermissionManager
import org.devio.takephoto.permission.TakePhotoInvocationHandler
import java.io.File

/**
 *@date     创建时间： 2018/11/2
 *@author   创建人：杨庆
 *@descript 描述：
 *@version
*/
open class TakePhotoBaseActivity: BaseActiviy() , TakePhoto.TakeResultListener, InvokeListener {
    private val TAG = TakePhotoActivity::class.java.name
    private var takePhot: TakePhoto? = null
    private var invokeParam: InvokeParam? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getTakePhoto().onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        getTakePhoto().onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this)
    }

    override fun takeSuccess(result: TResult?) {
//        Log.i(TAG, "takeSuccess：" + result?.getImage()?.compressPath)
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.i(TAG, "takeFail:$msg")

    }

    override fun takeCancel() {
        Log.i(TAG, "操作取消")
    }

    override fun invoke(invokeParam: InvokeParam): PermissionManager.TPermissionType {
        val type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod())
        if (PermissionManager.TPermissionType.WAIT == type) {
            this.invokeParam = invokeParam
        }
        return type
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    fun getTakePhoto(): TakePhoto {
        if (takePhot == null) {
            takePhot = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, this)) as TakePhoto
            //默认配置
            val takePhotoOptions = TakePhotoOptions.Builder()
                    .setCorrectImage(true)//是否纠正旋转
                    .setWithOwnGallery(false)//当从相册选择时onPickFromGallery 可以设置是否从自定义的选择界面进行选择
                    .create()
            takePhot!!.setTakePhotoOptions(takePhotoOptions)
            val config = CompressConfig.Builder()
                    .enablePixelCompress(true)//是否开启像素压缩
                    .enableQualityCompress(true)//质量压缩
                    .enableReserveRaw(true)//是否保留原件
                    .create()
            takePhot!!.onEnableCompress(config,true)//压缩后的文件存放在了cache缓存文件夹
        }

        return takePhot as TakePhoto
    }


    /**
     * 获取文件保存位置
     */
    fun getFileUri(): Uri {
        val file = File(Environment.getExternalStorageDirectory(), "/菜谱图片/" + System.currentTimeMillis() + ".jpg")
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        val imageUri = Uri.fromFile(file)
        return imageUri
    }

}
package com.example.caipuandroid.mvp.p

import com.example.caipuandroid.base.BasePresenter
import com.example.caipuandroid.mvp.contract.AddGreensContract
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.vo.BurdenBean
import com.example.caipuandroid.ui.vo.Greens
import com.example.caipuandroid.ui.vo.MakesBean
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
import com.qiniu.android.common.FixedZone
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.*
import org.json.JSONObject
import java.lang.Exception

class AddGreensPresenter : BasePresenter<AddGreensContract.View>(), AddGreensContract.Presenter {
    val service by lazy { IServiceNetImpl() }
    var uploadManager: UploadManager? = null
    override fun getQiNiuToken() {

    }

    val imgsMap = HashMap<String, String>()
    override fun addGreens() {


        initQiniu()

        val greens: Greens = Greens()
        val name = mRootView?.getName()
        if (name.isNullOrBlank()) {
            mRootView!!.onError("名称不能为空")
            return
        }
        greens.name = name
        val tips = mRootView?.getTips()
        greens.tips = tips
        greens.brief ="空brief"

        var burdenStr = ""
        val burdens: ArrayList<BurdenBean> = mRootView?.getBurdens()!!
        burdens.forEach { burden ->
            if (burden.name.isNotEmpty() && burden.dosage.isNotEmpty()) {
                burdenStr += burden.name + ":" + burden.dosage + ";"
            }
        }
        greens.burden = burdenStr
        greens.views = 0
        greens.collect = 0

        val img = mRootView?.getCoverImg()
        if (img.isNullOrBlank()) {
            mRootView?.onError("请添加封面图")
            return
        }
        val maks: ArrayList<MakesBean> = mRootView?.getMakes()!!


        imgsMap.put("coverImg", img!!)
        for (mak in maks) {
            if (mak.step.isNotEmpty() && mak.stepImg.isNotEmpty()) {
                imgsMap.put(mak.step, mak.stepImg)
            }
        }

        try {
            service.getQiNiuToken().excute().subscribe({ token ->
                //获取完token 先上传图片到七牛
                submitData(greens, token)

//                mRootView?.onError(token)
            }, {
                Klog.e(contents = it.message!!)
                mRootView?.onError(it.message!!)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val uploadedImg = HashMap<String, String>()
    private fun submitData(greens: Greens, token: String) {
        Klog.e(contents = token)
        if (imgsMap.size > 0) {

            var firstKey: String = ""
            imgsMap.iterator().forEach { it ->
                if (it.key.isNotEmpty()) {
                    firstKey = it.key
                    return@forEach
                }
            }
            val filePath = imgsMap.get(firstKey)
            val key = System.currentTimeMillis().toString()
            uploadManager!!.put(filePath, key, token, object : UpCompletionHandler {
                override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                    if (info!!.isOK()) {
                        Klog.e(contents = response!!["key"])
                        uploadedImg.put(firstKey, response!!["key"].toString())
                        imgsMap.remove(firstKey)
                        submitData(greens, token)
                    } else {
                        mRootView?.onError("图片上传失败")
                        return
                    }
                }
            }, null)

        } else {
            Klog.e(contents = "数据全部提交完毕")
            greens.img = uploadedImg.get("coverImg")

            var maksStr = ""
            uploadedImg.iterator().forEach { it ->
                if (!it.key.equals("coverImg")) {
                    maksStr += it.key + "$$" + it.value + "||"
                }
            }

            greens.makes = maksStr
            service.addGreens(greens)
                .excute().subscribe({
                    if (it) {
                        mRootView?.addSuccess()
                    } else {
                        mRootView?.onError("添加失败")
                    }
                },{
                    mRootView?.onError(it.message!!)
                })
        }


    }

    fun initQiniu() {
        if (uploadManager == null) {
            val config = Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build()
            // 重用uploadManager。一般地，只需要创建一个uploadManager对象
            uploadManager = UploadManager(config)
        }
    }


}
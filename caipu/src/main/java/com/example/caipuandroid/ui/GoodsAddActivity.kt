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
    override fun addSuccess() {
        toast("添加陈宫")
        finish()
    }

    override fun getCoverImg(): String {
        return coverImg
    }

    override fun getName(): String {
        return etName.text.toString()
    }

    override fun getTips(): String {
        return etExPerience.text.toString()
    }

    override fun getBurdens(): ArrayList<BurdenBean> {
        return burdens;
    }

    override fun getMakes(): ArrayList<MakesBean> {
        return makesSteps
    }

    override fun getPresenter(): AddGreensContract.Presenter {
        return AddGreensPresenter()
    }

    override fun attachView(presenter: AddGreensContract.Presenter) {
        presenter.attachView(this)
    }


    private var coverImg = ""//封面图地址
    var imgTag: String = ""
    var stepView: ImageView? = null
    var mPostion: Int = 0//步骤图片

    private val makesSteps: ArrayList<MakesBean> = ArrayList()
    private val burdens: ArrayList<BurdenBean> = ArrayList()


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
        for (i in 0 until 1) {
            val burdenBean = BurdenBean("", "")
            burdens.add(burdenBean)
        }
        burdenAdapter.setNewData(burdens)
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
            mPresenter.addGreens()

        }
    }

    override fun onError(message: String) {
        super.onError(message)
        etExPerience.post {
            toast(message)
        }

    }


    override fun takeSuccess(result: TResult?) {
        if (imgTag.equals("ivStepImg")) {
            try {
                if (stepView != null) {
                    makesSteps[mPostion].stepImg = result!!.image.compressPath
                    Glide.with(this).load(result!!.image.compressPath).into(stepView!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Glide.with(this).load(result!!.image.compressPath).into(ivCover)
            coverImg = result!!.image.compressPath
        }
    }
}
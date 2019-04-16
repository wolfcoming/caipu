package com.example.caipuandroid.ui.loader

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader
import com.youth.banner.loader.ImageLoaderInterface
import java.lang.Exception

/**
 * Author：yangqing
 * Time：2019/4/16 11:47
 * Description：
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        try {
            Glide.with(context!!).load(Uri.parse(path as String)).into(imageView!!)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}
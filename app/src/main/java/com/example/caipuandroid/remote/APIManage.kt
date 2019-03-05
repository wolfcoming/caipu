package com.example.caipuandroid.remote

import android.os.Environment
import com.example.caipuandroid.BuildConfig
import com.infoholdcity.baselibrary.MainApplaction
import com.example.caipuandroid.utils.NetWorkHelper
import com.infoholdcity.basearchitecture.net.interceptor.HttpCacheInterceptor
import com.infoholdcity.baselibrary.annotation.BaseUrl
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *@date     创建时间： 2018/9/29
 *@author   创建人：杨庆
 *@descript 描述：
 *@version
 */
class APIManage private constructor() {
    private var apiService: APIService? = null
    private var retrofit: Retrofit? = null

    companion object {
        @Volatile
        private var INSTANCE: APIManage? = null
        //获取单例
        val instance: APIManage
            get() {
                if (INSTANCE == null) {
                    synchronized(APIManage::class.java) {
                        INSTANCE = APIManage()
                    }
                }
                return INSTANCE!!
            }
    }

    private fun getRetrofit(): Retrofit {
        if (retrofit != null)
            return retrofit!!
        val client = initOkHttp()
        retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(URLConfig.baseurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        return retrofit!!
    }

    //api 调用方式一 ：需要自己生产service实例 在该类配置baseUrl
    public fun apiService(): APIService {
        if (apiService == null) {
            synchronized(APIManage::class.java) {
                apiService = getRetrofit().newBuilder().baseUrl(URLConfig.baseurl)
                        .build()
                        .create(APIService::class.java)
            }
        }
        return apiService!!
    }


    //调用方式2：注解方式，需要在接口上手动配置baseURL
    fun <T> getRequest(api: Class<T>): T {
        //获取api类上的注解
        var annotation: BaseUrl? = api.getAnnotation(BaseUrl::class.java)
        verfiyAnnotation(annotation)
        var baseUrl = annotation!!.value
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/"
        }
        val oldBaseUrl = getRetrofit().baseUrl().url().toString()
        if (!baseUrl.equals(oldBaseUrl)) {
            retrofit = retrofit?.newBuilder()?.baseUrl(baseUrl)?.build()
        }
        return retrofit!!.create(api)
    }

    private fun verfiyAnnotation(annotation: BaseUrl?) {
        if (annotation == null)
            throw NullPointerException("\n" +
                    "==================================================================" +
                    "\n" +
                    "\n" +
                    "请在接口类上定义 baseUrl：参考实例：" +
                    "\n" +
                    "\n" +
                    "@BaseUrl(UrlConfig.BaseWeatherUrl)\n" +
                    "interface WeatherApi {\n" +
                    "    @GET(\"weather_mini\")\n" +
                    "    fun getWeather(@Query(\"city\") city: String): Observable<WeatherBean>\n" +
                    "}" +
                    "" +
                    "" +
                    "\n" +
                    "\n" +
                    "==================================================================" +
                    "")
    }


    /**
     * 初始化okhttp的基本配置
     */
    private fun initOkHttp(): OkHttpClient {
        // 初始化okhttp
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        //设置缓存100M
        val cache = Cache(File(Environment.getExternalStorageDirectory()
                , "httpCache"), 1024 * 1024 * 100)
        val client = builder
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(addQueryParameterInterceptor)
                .addInterceptor(headerInterceptor)
                .addNetworkInterceptor(HttpCacheInterceptor())//只在有网的情况下执行
                .addInterceptor(networkStatusInterceptor)//有网无网都执行
                .build()
        return client!!
    }


    //添加请求头
    val headerInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    //公共参数
    val addQueryParameterInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
                .addQueryParameter("platform", "android")
                .addQueryParameter("version", "1.0.0")
                .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        chain.proceed(request)
    }

    //检测网络是否连接
    val networkStatusInterceptor = Interceptor { chain ->
        var request = chain.request()
        if (!NetWorkHelper.isNetConnected(MainApplaction.getContext()!!)) {
            val maxStale = 4 * 7 * 24 * 60; // 离线时缓存保存4周,单位:秒
            val tempCacheControl:CacheControl =  CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxStale, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();

        }
        chain.proceed(request);
    }


}
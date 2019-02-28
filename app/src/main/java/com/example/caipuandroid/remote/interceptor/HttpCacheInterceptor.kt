package com.infoholdcity.basearchitecture.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author：sun
 * Time：2018/10/9 15:13
 * Description：
 */
class HttpCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val response = chain.proceed(request)
        val maxAge = 10; // read from cache for 10 seconds
        return response.newBuilder()
                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public,max-age=$maxAge").build();
    }
}
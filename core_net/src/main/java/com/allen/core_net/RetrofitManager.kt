package com.allen.core_net

import com.allen.core_net.api.UriConstant
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Allen
 * on 2018/12/23
 */
object RetrofitManager {
    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    private val service : ApiService by lazy { getRetrofit(UriConstant.BASE_URL)!!.create(ApiService::class.java) }
   // private var token: String by Preference("","")

    fun getRetrofit(baseUrl:String): Retrofit? {
        if (retrofit == null) {
                //添加一个log拦截器,打印所有的log
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                //可以设置请求过滤的水平,body,basic,headers
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                //设置 请求的缓存的大小跟位置
               // val cacheFile = File(context.cacheDir, "cache")
                //val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

                client = OkHttpClient.Builder()
                        .addInterceptor(addQueryParameterInterceptor())  //参数添加
                        .addInterceptor(addHeaderInterceptor()) // token过滤
//                            .addInterceptor(addCacheInterceptor())
                        .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                        //.cache(cache)  //添加缓存
                        .connectTimeout(60L, TimeUnit.SECONDS)
                        .readTimeout(60L, TimeUnit.SECONDS)
                        .writeTimeout(60L, TimeUnit.SECONDS)
                        .build()

                // 获取retrofit的实例
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)  //自己配置
                        .client(client!!)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        }
        return retrofit
    }
    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    //.header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }
}



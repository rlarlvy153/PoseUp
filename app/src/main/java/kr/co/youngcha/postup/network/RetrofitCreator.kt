package kr.co.youngcha.postup.network

import kr.co.youngcha.postup.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCreator {
    val API_BASE_URL = "https://postup.dev.youngcha.co.kr/api/"
//    val API_BASE_URL = "http://192.168.43.133:8080/api/"
    private var retrofit: Retrofit
    private var okHttpClient: OkHttpClient

    init {
        okHttpClient =
            createOkHttpClient()

        retrofit = Retrofit.Builder().apply {
            baseUrl(API_BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()


    }

    private fun defaultRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())
            .build()

    }

    fun <T> create(service: Class<T>): T {
        return defaultRetrofit().create(service)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            
            .build()


    }


    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }

}
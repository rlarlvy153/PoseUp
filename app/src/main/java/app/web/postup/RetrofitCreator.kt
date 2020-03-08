package app.web.postup

import com.web.postup.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {
    companion object{
        val API_BASE_URL = "https://pupost.youngcha.co.kr/mock/"

        private fun defaultRetrofit(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build()

        }
        fun <T> create(service: Class<T>):T{
            return defaultRetrofit().create(service)
        }

        private fun createOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            if(BuildConfig.DEBUG){
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            else{
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()


        }
    }
}
package ir.sinamomken.karafs_interview1.data.remote

import android.util.Base64
import ir.sinamomken.karafs_interview1.data.remote.api.SampleApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private const val BASE_URL = "http://karafsapp.com/"

    private val AUTH = "Basic "+ Base64.encodeToString("username:password".toByteArray(), Base64.NO_WRAP)

    private val loggingInterceptor : HttpLoggingInterceptor

    private val okHttpClient: OkHttpClient

    private var ourInstance : Retrofit? = null
    val instance: Retrofit
        get(){
            if(ourInstance == null){
                ourInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return ourInstance!!
        }

    init {
        loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = OkHttpClient.Builder().addInterceptor(
            loggingInterceptor
        ).build()

    }

    private var ourSampleApiInstance : SampleApi? = null
    val sampleApiInstance : SampleApi
        get() {
            if(ourSampleApiInstance == null){
                ourSampleApiInstance = RetrofitClient.instance.create(SampleApi::class.java)
            }
            return ourSampleApiInstance!!
        }



    fun encodeToBase64(username: String, password: String): String{
        val userPassStr: String = username +":" +password
        val authStr = "Basic "+ Base64.encodeToString(userPassStr.toByteArray(), Base64.NO_WRAP)
        return authStr
    }
}
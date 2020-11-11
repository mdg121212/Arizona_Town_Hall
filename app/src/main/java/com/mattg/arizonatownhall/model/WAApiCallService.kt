package com.mattg.arizonatownhall.model


import android.util.Base64
import com.mattg.arizonatownhall.BuildConfig
import com.mattg.arizonatownhall.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WAApiCallService {


   // private  val BEARER_AUTH_HEADER_VALUE = "Bearer ${Constants.token}"
   // private val BEARER_AUTH_HEADER_REFRESH_VALUE = "Bearer ${Constants.refreshToken}"

   // private const val AUTH_KEY2 = "${Constants.apiClientId}:${Constants.apiClientSecret}"
    private const val API_KEY = "APIKEY:${Constants.apiKey}"
   //  private  val CLIENT_SECRET_KEY = "Basic " + Base64.encodeToString(AUTH_KEY2.toByteArray(), Base64.NO_WRAP)
    private const val BASE_URL = "https://oauth.wildapricot.org/"
    private const val BASE_API_URL = "https://api.wildapricot.org/v2.2/"
    private val AUTH = "Basic " + Base64.encodeToString(API_KEY.toByteArray(), Base64.NO_WRAP)



    private var api:WAApiCall? = null
    private var apiGet:WAApiCall? = null

    private fun getWAApi() : WAApiCall {

        if(api==null) {

            val okHttpClient = OkHttpClient.Builder()
                //add custom interceptor
                okHttpClient.addInterceptor { chain ->
                    //get the outgoing request
                    val request = chain.request()
                    //  Log.d("CALL", request.body.toString())

                    //add another header to this request, its been built but build a new one
                    val newRequest = request.newBuilder()
                        .addHeader("Host", "oauth.wildapricot.org")
                        .addHeader(
                            "Authorization",
                            AUTH
                        )
                        .method(request.method, request.body)
                        .build()
                   // Log.d("CALL", "$newRequest")
                    chain.proceed(newRequest)

                }

                api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build()
                    .create(WAApiCall::class.java)

            }

        return api!!

    }

    private fun getWAApiGet(): WAApiCall{

    if(apiGet == null){
        val okHttpClientGet = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        if (BuildConfig.DEBUG) {
            okHttpClientGet.addInterceptor(logging)
        }
        okHttpClientGet.addInterceptor { chain ->
            //get the outgoing request
            val request = chain.request()
            // Log.d("CALL", request.body.toString())

            //add another header to this request, its been built but build a new one
            val newRequest = request.newBuilder()
                .addHeader("Host", "api.wildapricot.com")
                .addHeader("Accept", "application/json")
                .method(request.method, request.body)
                .build()
            // Log.d("CALL", "$newRequest")
            chain.proceed(newRequest)

        }

        apiGet = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientGet.build())
            .build()
            .create(WAApiCall::class.java)

    }
    return apiGet!!

    }

//    fun getAuthorizedApi() =
//        Retrofit.Builder()
//            .baseUrl(BASE_API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(WAApiCall::class.java)


    fun get(): WAApiCall = getWAApiGet()

    fun call() =
        getWAApi()
}
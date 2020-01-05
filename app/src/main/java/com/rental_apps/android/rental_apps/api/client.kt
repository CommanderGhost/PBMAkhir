package com.rental_apps.android.rental_apps.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object client {
    const val BASE_URL = "http://192.168.43.98/rental-api/"
    const val baseUrlImage = "http://192.168.43.98/rental-api/upload/avatars/"
    const val baseImg = "http://192.168.43.98/rental-api/upload/"//Builder Retrofit
    //    http://192.168.43.221/rental-api/";
//    private  static  final  String BASE_URL="http://192.168.43.98/rental-api/";
//    private  static  final  String BASE_URL_IMAGE="192.168.43.98/rental-api/upload/avatars/";
//    private  static  final  String BASE_URL_IMG="http://192.168.43.98/rental-api/upload/";
    @JvmStatic
    val api: request
        get() { //Builder Retrofit
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(request::class.java)
        }

    // Request customization: add request headers
    val json: request
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("x-access-token", "eyJhbGci")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(request::class.java)
        }

}
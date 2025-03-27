package com.example.flamespace.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitHelper {
    companion object{
        private const val BASE_URL = "https://slimy-flowers-remain.loca.lt/"

        fun getService(): ServiceAPI{
            val loggingInterceptor = HttpLoggingInterceptor().apply{
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ServiceAPI::class.java)
        }
    }
}



//https://quiet-beach-04492-d8a02ae21cbc.herokuapp.com/
//https://jsonplaceholder.typicode.com/


//object RetrofitHelper {
//
//    private const val BASE_URL = "https://quiet-beach-04492-d8a02ae21cbc.herokuapp.com/"
//
//    fun getInstance(): Retrofit {
//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client) // Set the custom OkHttpClient
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//}
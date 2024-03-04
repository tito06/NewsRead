package com.example.newsread

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("Android/news-api-feed/staticResponse.json")
    suspend fun getNews() :Response<NewsData>


/*    companion object{
        var apiService: ApiService? = null
        fun getInstacne(): ApiService {
            if(apiService == null){
                var retrofit = Retrofit.Builder()
                    .baseUrl("https://candidate-test-data-moengage.s3.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                apiService = retrofit.create(ApiService::class.java)
            }

            return  apiService!!
        }
    }*/
}
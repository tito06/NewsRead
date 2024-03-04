package com.example.newsread

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonConvertor(): GsonConverterFactory {
        return  GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://candidate-test-data-moengage.s3.amazonaws.com/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }


    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit):ApiService{
        return  retrofit.create(ApiService::class.java)
    }

}
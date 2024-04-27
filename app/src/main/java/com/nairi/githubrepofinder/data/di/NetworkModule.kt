package com.nairi.githubrepofinder.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nairi.githubrepofinder.data.api.WebApi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        getRetrofit(
            context = androidContext(),
            client = get()
        )
    }
    single {
        getApi(
            retrofit = get(),
        )
    }

    single {
        getClient(context = androidContext())
    }


}

private fun getRetrofit(
    context: Context,
    client: OkHttpClient
): Retrofit = Retrofit
    .Builder()
    .baseUrl("https://api.github.com")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


private fun getClient(context: Context) = OkHttpClient().newBuilder()
    .addInterceptor(
        ChuckerInterceptor.Builder(context).build()
    )
    .build()

private fun getApi(
    retrofit: Retrofit
): WebApi {
    return retrofit.create(WebApi::class.java)
}
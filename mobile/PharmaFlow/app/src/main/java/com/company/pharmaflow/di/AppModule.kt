package com.company.pharmaflow.di

import com.company.pharmaflow.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
//    single {
//        val loggingInterceptor = if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        } else {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//        }
//        val client = OkHttpClient.Builder().apply {
//            addInterceptor(loggingInterceptor)
//        }.build()
//
//        val retrofit = Retrofit.Builder().apply {
//            baseUrl(BuildConfig.API_BASE_URL)
//            addConverterFactory(GsonConverterFactory.create())
//            client(client)
//        }.build()
//        retrofit.create(ApiService::class.java)
//    }

//    single<DrugsRepository> {
//        DrugsRepositoryImpl(get())
//    }

//    viewModel{
//        HomeViewModel(get())
//    }

//    viewModel{
//        CameraViewModel(get())
//    }

    viewModel {
        MainViewModel()
    }

//    viewModel{
//        DetailViewModel(get())
//    }
}
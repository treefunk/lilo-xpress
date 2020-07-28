package com.myoptimind.lilo_xpress.di

import android.os.Build
import com.myoptimind.lilo_xpress.BuildConfig
import com.myoptimind.lilo_xpress.api.GuestLoginService
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder().addHeader(
                    "x-api-key",
                    BuildConfig.APIKEY
                ).build()
            )
        }.connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()


        return Retrofit.Builder()
            .baseUrl("http://lilo.blitzworx.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGuestLoginService(
        retrofit: Retrofit
    ): GuestLoginService {
        return retrofit.create(GuestLoginService::class.java)
    }


    @Singleton
    @Provides
    fun provideDropDownDataSource(guestLoginService: GuestLoginService): DropdownDataSource {
        return DropdownDataSource(guestLoginService)
    }

}
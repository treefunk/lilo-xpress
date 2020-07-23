package com.myoptimind.lilo_xpress.di

import com.myoptimind.lilo_xpress.api.GuestLoginService
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GuestLoginModule {

    @ActivityRetainedScoped
    @Provides
    fun provideGuestLoginService(
       retrofit: Retrofit
    ): GuestLoginService {
        return retrofit.create(GuestLoginService::class.java)
    }

    @ActivityRetainedScoped
    @Provides
    fun provideGuestRepository(guestLoginService: GuestLoginService) = GuestLoginRepository(guestLoginService)

}
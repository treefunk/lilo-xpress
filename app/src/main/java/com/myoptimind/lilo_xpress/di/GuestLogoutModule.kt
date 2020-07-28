package com.myoptimind.lilo_xpress.di

import com.myoptimind.lilo_xpress.api.GuestLogoutService
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutRepository
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GuestLogoutModule {

    @ActivityRetainedScoped
    @Provides
    fun provideGuestLogoutService(retrofit: Retrofit): GuestLogoutService {
        return retrofit.create(GuestLogoutService::class.java)
    }

    @ActivityRetainedScoped
    @Provides
    fun provideGuestLogoutRepository(
        guestLogoutService: GuestLogoutService
    ): GuestLogoutRepository {
        return GuestLogoutRepository(
            guestLogoutService
        )

    }
}
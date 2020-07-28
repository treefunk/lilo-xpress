package com.myoptimind.lilo_xpress.di

import com.myoptimind.lilo_xpress.api.GuestLoginService
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginRepository
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
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
    fun provideGuestRepository(guestLoginService: GuestLoginService, dropdownDataSource: DropdownDataSource)
            = GuestLoginRepository(dropdownDataSource,guestLoginService)
}
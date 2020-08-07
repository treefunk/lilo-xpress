package com.myoptimind.lilo_xpress.di

import com.myoptimind.lilo_xpress.cesbie.CesbieRepository
import com.myoptimind.lilo_xpress.cesbie.api.CesbieService
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit


@Module
@InstallIn(ActivityRetainedComponent::class)
class CesbieModule {

    @ActivityRetainedScoped
    @Provides
    fun provideCesbieService(retrofit: Retrofit): CesbieService {
        return retrofit.create(CesbieService::class.java)
    }

    @ActivityRetainedScoped
    @Provides
    fun provideCesbieRepository(
        dropdownDataSource: DropdownDataSource,
        cesbieService: CesbieService
    ): CesbieRepository {
        return CesbieRepository(dropdownDataSource,cesbieService)
    }
}
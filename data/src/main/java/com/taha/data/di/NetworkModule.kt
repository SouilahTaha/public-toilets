package com.taha.data.di

import com.taha.data.network.api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

  @Provides
  fun provideApi() = api
}
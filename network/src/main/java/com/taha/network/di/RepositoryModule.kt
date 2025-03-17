package com.taha.network.di


import com.taha.data.repository.PublicToiletsRepositoryImpl
import com.taha.domain.repository.PublicToiletsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindProjectRepository(repository: PublicToiletsRepositoryImpl): PublicToiletsRepository
}
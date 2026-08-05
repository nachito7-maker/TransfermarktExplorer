package io.idolz.transfermarketexplorer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.idolz.transfermarketexplorer.data.repository.TransfermarketRepositoryImpl
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransfermarketRepository(
        transfermarketRepositoryImpl: TransfermarketRepositoryImpl
    ): TransfermarketRepository
}
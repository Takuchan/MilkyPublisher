package com.takuchan.milkypublisher.di


import com.takuchan.milkypublisher.analysis.PoseCaptureImageAnalyzer
import com.takuchan.milkypublisher.preference.UDPController
import com.takuchan.milkypublisher.repository.ReceiveUdpRepository
import com.takuchan.milkypublisher.viewmodel.ControllerViewModel
import com.takuchan.milkypublisher.viewmodel.UDPFlowViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule{
    @Provides
    @Singleton
    fun provideMyViewModel(): ControllerViewModel {
        return ControllerViewModel()
    }

    // UDP通信を行うReceiveUdpRepositoryをHiltでインスタンス化させる
    @Provides
    fun provideReceiveUdpRepository(
        udpController: UDPController
    ): ReceiveUdpRepository {
        return ReceiveUdpRepository(udpController)
    }


}


package com.takuchan.milkypublisher.di


import com.takuchan.milkypublisher.analysis.PoseCaptureImageAnalyzer
import com.takuchan.milkypublisher.preference.UDPController
import com.takuchan.milkypublisher.repository.ReceiveUdpRepository
import com.takuchan.milkypublisher.viewmodel.ControllerViewModel
import com.takuchan.milkypublisher.viewmodel.UDPFlowViewModel





object ViewModelModule{

    fun provideMyViewModel(): ControllerViewModel {
        return ControllerViewModel()
    }

    // UDP通信を行うReceiveUdpRepositoryをHiltでインスタンス化させる
    fun provideReceiveUdpRepository(
        udpController: UDPController
    ): ReceiveUdpRepository {
        return ReceiveUdpRepository(udpController)
    }


}


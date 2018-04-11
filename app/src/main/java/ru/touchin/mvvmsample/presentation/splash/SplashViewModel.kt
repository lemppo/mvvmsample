package ru.touchin.mvvmsample.presentation.splash

import ru.touchin.mvvmsample.domain.global.models.ConfigurationRepository
import ru.touchin.mvvmsample.presentation.base.BaseViewModel
import ru.touchin.mvvmsample.presentation.base.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val configuration: ConfigurationRepository) : BaseViewModel() {

    var ready: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        getConfiguration()
    }

    fun getConfiguration() = untilDestroy(configuration.getConfiguration(),
             { ready.value = true },
             { ready.value = true })
}
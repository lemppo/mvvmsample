package ru.touchin.mvvmsample.presentation.splash

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import ru.touchin.mvvmsample.presentation.base.BaseViewModel
import javax.inject.Inject
import ru.touchin.mvvmsample.domain.splash.SplashInteractor

class SplashViewModel @Inject internal constructor(application: Application,
                                                   var splashInteractor: SplashInteractor) : BaseViewModel(application) {

    var ready: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getConfiguration()
    }

    fun getConfiguration() = untilDestroy(splashInteractor.getConfiguration(),
             { ready.value = true },
             { ready.value = true })
}
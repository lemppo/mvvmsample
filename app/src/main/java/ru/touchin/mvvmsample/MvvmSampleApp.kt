package ru.touchin.mvvmsample

import android.app.Application
import ru.touchin.mvvmsample.di.global.ApplicationComponent
import ru.touchin.mvvmsample.di.global.DaggerApplicationComponent
import ru.touchin.mvvmsample.di.global.modules.ApplicationModule
import ru.touchin.mvvmsample.di.global.modules.NetworkModule
import ru.touchin.mvvmsample.di.viewmodel.ViewModelFactory
import javax.inject.Inject

class MvvmSampleApp : Application() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        lateinit var instance: MvvmSampleApp
    }

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(NetworkModule())
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }
}

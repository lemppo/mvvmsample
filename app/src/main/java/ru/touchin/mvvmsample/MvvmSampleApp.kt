package ru.touchin.mvvmsample

import android.app.Application
import android.content.Context
import ru.touchin.mvvmsample.di.global.ApplicationComponent
import ru.touchin.mvvmsample.di.global.DaggerApplicationComponent
import ru.touchin.mvvmsample.di.global.modules.ApplicationModule
import ru.touchin.mvvmsample.di.global.modules.ConfigurationModule
import ru.touchin.mvvmsample.di.global.modules.NetworkModule

class MvvmSampleApp : Application() {

    companion object {
        lateinit var instance: MvvmSampleApp
    }

    lateinit var applicationComponent: ApplicationComponent

//    override fun isDebug() = BuildConfig.DEBUG

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(NetworkModule())
                .configurationModule(ConfigurationModule(this))
//                .viewModelModule(ViewModelModule())
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

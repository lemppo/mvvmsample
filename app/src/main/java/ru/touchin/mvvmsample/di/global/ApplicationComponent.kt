package ru.touchin.mvvmsample.di.global

import dagger.Component
import ru.touchin.mvvmsample.di.global.modules.ApplicationModule
import ru.touchin.mvvmsample.di.global.modules.ConfigurationModule
import ru.touchin.mvvmsample.di.global.modules.NetworkModule
import ru.touchin.mvvmsample.di.viewmodel.ViewModelFactoryWrapper
import ru.touchin.mvvmsample.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class, ConfigurationModule::class, ViewModelModule::class, ApplicationModule::class))
interface ApplicationComponent {

    fun inject(viewModelFactoryWrapper: ViewModelFactoryWrapper)
}
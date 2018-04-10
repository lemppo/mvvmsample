package ru.touchin.mvvmsample.di.global

import dagger.Component
import ru.touchin.mvvmsample.MvvmSampleApp
import ru.touchin.mvvmsample.di.global.modules.ApplicationModule
import ru.touchin.mvvmsample.di.global.modules.NetworkModule
import ru.touchin.mvvmsample.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class), (ViewModelModule::class), (ApplicationModule::class)])
interface ApplicationComponent {

    fun inject(viewModelFactoryWrapper: MvvmSampleApp)
}
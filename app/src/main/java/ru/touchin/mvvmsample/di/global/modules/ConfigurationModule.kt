package ru.touchin.mvvmsample.di.global.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.touchin.mvvmsample.data.network.TMDApi
import ru.touchin.mvvmsample.domain.global.models.ConfigurationModel
import javax.inject.Singleton

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Module
class ConfigurationModule(val context: Context) {
    @Provides
    @Singleton
    fun provideConfigurationModel(api: TMDApi) = ConfigurationModel(context, api)
}
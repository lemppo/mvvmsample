package ru.touchin.mvvmsample.domain.splash

import ru.touchin.mvvmsample.domain.global.models.ConfigurationModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Singleton
class SplashInteractor @Inject internal constructor(var configuration: ConfigurationModel) {

    fun getConfiguration() = configuration.getConfiguration()
}
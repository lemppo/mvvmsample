package ru.touchin.mvvmsample.domain.global.models

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.touchin.mvvmsample.data.PrefsRepository
import ru.touchin.mvvmsample.data.network.TMDApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Singleton
class ConfigurationRepository @Inject constructor(
        private val api: TMDApi,
        private val prefsRepository: PrefsRepository
) {

    companion object {
        var IMAGE_BASE_URL = ""
    }

    fun getConfiguration() =
            api
                    .configuration()
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        val body = it.body()
                        if (it.isSuccessful && body != null) {
                            var size = ""
                            // I have no idea which image size to take, so let`s
                            // consider, just for example, the third one to be preferable
                            when (body.images.poster_sizes.size) {
                                1 -> size = body.images.poster_sizes[0]
                                2 -> size = body.images.poster_sizes[1]
                                in 3..Int.MAX_VALUE -> size = body.images.poster_sizes[2]
                            }
                            IMAGE_BASE_URL = body.images.base_url + size + "/"
                            prefsRepository.imageBaseUrl = IMAGE_BASE_URL
                        }
                    }
                    .onErrorReturn {
                        IMAGE_BASE_URL = prefsRepository.imageBaseUrl
                        it.printStackTrace()
                    }
}
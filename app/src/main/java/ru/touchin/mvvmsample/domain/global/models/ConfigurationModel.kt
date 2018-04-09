package ru.touchin.mvvmsample.domain.global.models

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.data.network.TMDApi

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
class ConfigurationModel(private val context: Context, private val api: TMDApi) {

    companion object {
        val IMG_BASE_URL_KEY = "IMG_BASE_URL_KEY"
        var IMAGE_BASE_URL = ""
    }

    fun getConfiguration() =
            api
                    .configuration()
                    .subscribeOn(Schedulers.io())
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
                            val prefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                            prefs.edit().apply { putString(IMG_BASE_URL_KEY, IMAGE_BASE_URL) }.apply()
                        }
                    }
                    .onErrorReturn {
                        IMAGE_BASE_URL = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(IMG_BASE_URL_KEY, "")
                        it.printStackTrace()
                    }
}
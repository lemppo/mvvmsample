package ru.touchin.mvvmsample.data.network.response

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
data class ConfigurationResponse(
        val images: ImagesConfiguration
)

data class ImagesConfiguration(
        val base_url: String,
        val poster_sizes: List<String>
)
package ru.touchin.mvvmsample.data.network.response

import ru.touchin.kotlinsamples.data.database.Movie

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
data class MoviesResponse(
        val page: Int,
        val total_pages: Int,
        val results: List<Movie>
)
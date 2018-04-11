package ru.touchin.mvvmsample.domain.moviedetails

import ru.touchin.kotlinsamples.data.database.Movie
import ru.touchin.mvvmsample.domain.global.models.ConfigurationRepository
import java.io.Serializable
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
data class MovieWrapper(
        val id: Int = 0,
        val rating: Float = 0f,
        val title: String = "",
        val overview: String = "",
        val releaseDate: Date = Date(),
        val posterPath: String = ""
) : Serializable {

    companion object {
        fun create(movie: Movie) = MovieWrapper(movie.id,
                movie.vote_average,
                movie.title,
                movie.overview,
                movie.release_date,
                movie.poster_path)
    }

    val ratingString = DecimalFormat("#.#").format(rating)
    val dateString = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(releaseDate)

    val imagePath = ConfigurationRepository.IMAGE_BASE_URL + posterPath
}
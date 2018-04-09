package ru.touchin.mvvmsample.domain.moviedetails

import ru.touchin.kotlinsamples.data.database.Movie
import ru.touchin.mvvmsample.domain.global.models.ConfigurationModel
import java.io.Serializable
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
class MovieWrapper(
        var id: Int = 0,
        var rating: Float = 0f,
        var title: String = "",
        var overview: String = "",
        var releaseDate: Date = Date(),
        var posterPath: String = ""
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

    val imagePath = ConfigurationModel.IMAGE_BASE_URL + posterPath
}
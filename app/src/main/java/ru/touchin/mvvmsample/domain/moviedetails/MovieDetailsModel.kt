package ru.touchin.mvvmsample.domain.moviedetails

import java.io.Serializable

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
sealed class MovieDetailsModel : Serializable {
    class Movie(val movie: MovieWrapper) : MovieDetailsModel()

    class Error(val error: Throwable) : MovieDetailsModel()
}
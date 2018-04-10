package ru.touchin.mvvmsample.domain.moviedetails

import io.reactivex.schedulers.Schedulers
import ru.touchin.kotlinsamples.data.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Singleton
class MovieDetailsInteractor @Inject constructor(private val db: AppDatabase) {

    fun getMovie(movieId: Int) = db.movieDao().getMovie(movieId)
            .subscribeOn(Schedulers.io())
            .map {
                MovieDetailsModel.Movie(MovieWrapper.create(it))
            }
}
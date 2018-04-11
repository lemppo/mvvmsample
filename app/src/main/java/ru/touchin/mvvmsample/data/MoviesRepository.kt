package ru.touchin.mvvmsample.data

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import io.reactivex.Single
import retrofit2.Response
import ru.touchin.kotlinsamples.data.database.AppDatabase
import ru.touchin.kotlinsamples.data.database.Movie
import ru.touchin.mvvmsample.data.network.response.MoviesResponse
import ru.touchin.mvvmsample.data.network.TMDApi
import ru.touchin.mvvmsample.domain.moviedetails.MovieWrapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Singleton
class MoviesRepository @Inject constructor(
        private val api: TMDApi,
        private val db: AppDatabase,
        private val prefsRepository: PrefsRepository) {

    companion object {
        const val POPULAR = 1
        const val TOP_RATED = 2
    }

    fun movies(pageSize: Int): LiveData<PagedList<Movie>> {
        val boundaryCallback = MovieBoundaryCallback(this)
        // create a data source factory from Room
        val dataSourceFactory = db.movieDao().getMovies()
        val builder = LivePagedListBuilder(dataSourceFactory,
                PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setEnablePlaceholders(false)
                        .build())
                .setBoundaryCallback(boundaryCallback)
        return builder.build()
    }

    fun loadNextPage() {
        val mode = prefsRepository.mode
        val page = prefsRepository.page
        val totalPages = prefsRepository.totalPages
        if (page + 1 >= totalPages)
            return

        val response: Single<Response<MoviesResponse>> = when (mode) {
            POPULAR -> api.popularMovies(page + 1)
            TOP_RATED -> api.topRatedMovies(page + 1)
            else -> throw IllegalArgumentException("Unknown mode")
        }
        response
                .subscribe({
                    val body = it.body()
                    if (it.isSuccessful && body != null) {
                        prefsRepository.mode = mode
                        prefsRepository.page = body.page
                        prefsRepository.totalPages = body.total_pages
                        db.movieDao().insertAll(body.results)
                    } else {
                        // todo error
                    }
                }, {
                    it.printStackTrace()
                    // todo error
                })
    }

    fun loadFirstPage() {
        val mode = prefsRepository.mode

        val response: Single<Response<MoviesResponse>> = when (mode) {
            POPULAR -> api.popularMovies(1)
            TOP_RATED -> api.topRatedMovies(1)
            else -> throw IllegalArgumentException("Unknown mode")
        }
        response
                .subscribe({
                    val body = it.body()
                    if (it.isSuccessful && body != null) {
                        prefsRepository.mode = mode
                        prefsRepository.page = body.page
                        prefsRepository.totalPages = body.total_pages
                        db.clearAllTables()
                        db.movieDao().insertAll(body.results)
                    } else {
                        // todo error
                    }
                }, {
                    it.printStackTrace()
                    // todo error
                })
    }

    private fun getAllMoviesLocally() = mutableListOf<MovieWrapper>().apply {
        db.movieDao().getAllMovies().forEach { add(MovieWrapper.create(it)) }
    }
}

class MovieBoundaryCallback(
        private val moviesRepository: MoviesRepository)
    : PagedList.BoundaryCallback<Movie>() {

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        moviesRepository.loadFirstPage()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        moviesRepository.loadNextPage()
    }

    override fun onItemAtFrontLoaded(itemAtFront: Movie) {
        // ignored, since we only ever append to what's in the DB
    }
}
package ru.touchin.mvvmsample.data.network

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.support.annotation.MainThread
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import ru.touchin.kotlinsamples.data.database.AppDatabase
import ru.touchin.kotlinsamples.data.database.Movie
import ru.touchin.mvvmsample.data.network.response.MoviesResponse
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.domain.moviedetails.MovieWrapper
import javax.inject.Inject

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
class MoviesRepository @Inject internal constructor(var api: TMDApi,
                                                    var db: AppDatabase,
                                                    var context: Context) {

    companion object {
        const val POPULAR = 1
        const val TOP_RATED = 2
    }

    private val prefs by lazy { context.getSharedPreferences(context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE) }
    private val modeKey by lazy { context.getString(R.string.preference_mode_key) }
    private val pageKey by lazy { context.getString(R.string.preference_page_key) }
    private val totalPagesKey by lazy { context.getString(R.string.preference_total_pages_key) }

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
        val mode = prefs.getInt(modeKey, POPULAR)
        val page = prefs.getInt(pageKey, 0)
        val totalPages = prefs.getInt(totalPagesKey, 2)
        if (page + 1 >= totalPages)
            return

        val response: Single<Response<MoviesResponse>> = when (mode) {
            POPULAR -> api.popularMovies(page + 1)
            TOP_RATED -> api.topRatedMovies(page + 1)
            else -> throw IllegalArgumentException("Unknown mode")
        }
        response
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val body = it.body()
                    if (it.isSuccessful && body != null) {
                        prefs.edit().apply {
                            putInt(modeKey, mode)
                            putInt(pageKey, body.page)
                            putInt(totalPagesKey, body.total_pages)
                            apply()
                        }
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
        val mode = prefs.getInt(modeKey, POPULAR)

        val response: Single<Response<MoviesResponse>> = when (mode) {
            POPULAR -> api.popularMovies(1)
            TOP_RATED -> api.topRatedMovies(1)
            else -> throw IllegalArgumentException("Unknown mode")
        }
        response
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val body = it.body()
                    if (it.isSuccessful && body != null) {
                        prefs.edit().apply {
                            putInt(modeKey, mode)
                            putInt(pageKey, body.page)
                            putInt(totalPagesKey, body.total_pages)
                            apply()
                        }
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
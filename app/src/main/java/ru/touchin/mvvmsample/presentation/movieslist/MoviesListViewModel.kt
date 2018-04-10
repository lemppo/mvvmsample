package ru.touchin.mvvmsample.presentation.movieslist

import ru.touchin.mvvmsample.presentation.base.BaseViewModel
import javax.inject.Inject
import ru.touchin.mvvmsample.data.MoviesRepository
import ru.touchin.mvvmsample.data.MoviesRepository.Companion.POPULAR
import ru.touchin.mvvmsample.data.MoviesRepository.Companion.TOP_RATED
import ru.touchin.mvvmsample.data.PrefsRepository

class MoviesListViewModel @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val prefsRepository: PrefsRepository) : BaseViewModel() {

    var pagedItems = moviesRepository.movies(10)

    fun loadPopularMovies() {
        prefsRepository.mode = POPULAR
        moviesRepository.loadFirstPage()
    }

    fun loadTopRatedMovies() {
        prefsRepository.mode = TOP_RATED
        moviesRepository.loadFirstPage()
    }

    fun refreshMovies() = moviesRepository.loadFirstPage()
}
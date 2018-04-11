package ru.touchin.mvvmsample.presentation.moviedetails

import android.arch.lifecycle.MutableLiveData
import ru.touchin.mvvmsample.domain.moviedetails.MovieDetailsInteractor
import ru.touchin.mvvmsample.domain.moviedetails.MovieDetailsModel
import ru.touchin.mvvmsample.presentation.base.BaseViewModel
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieDetailsInteractor: MovieDetailsInteractor) : BaseViewModel() {

    var movieDetailsModel: MutableLiveData<MovieDetailsModel> = MutableLiveData()

    fun getMovie(movieId: Int) = untilDestroy(movieDetailsInteractor
            .getMovie(movieId), {
        movieDetailsModel.value = it
    }, {
        movieDetailsModel.value =  MovieDetailsModel.Error(it)
    }, {})
}
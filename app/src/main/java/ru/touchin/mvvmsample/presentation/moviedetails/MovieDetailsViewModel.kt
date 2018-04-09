package ru.touchin.mvvmsample.presentation.moviedetails

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.touchin.mvvmsample.domain.moviedetails.MovieDetailsInteractor
import ru.touchin.mvvmsample.domain.moviedetails.MovieDetailsModel
import ru.touchin.mvvmsample.presentation.base.BaseViewModel
import javax.inject.Inject

class MovieDetailsViewModel @Inject internal constructor(application: Application,
                                                         var movieDetailsInteractor: MovieDetailsInteractor) : BaseViewModel(application) {

    var movieDetailsModel: MutableLiveData<MovieDetailsModel> = MutableLiveData()

    fun getMovie(movieId: Int) = untilDestroy(movieDetailsInteractor
            .getMovie(movieId)
            .observeOn(AndroidSchedulers.mainThread()), {
        movieDetailsModel.value = it
    }, {
        movieDetailsModel.value =  MovieDetailsModel.Error(it)
    })
}
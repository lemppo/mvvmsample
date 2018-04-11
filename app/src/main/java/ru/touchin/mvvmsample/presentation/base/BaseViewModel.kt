package ru.touchin.mvvmsample.presentation.base

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val isCreatedSubject = BehaviorSubject.createDefault<Boolean>(true)

    fun <T> untilDestroy(
            flowable: Flowable<T>,
            onNextAction: (T) -> Unit,
            onErrorAction: (Throwable) -> Unit,
            onCompletedAction: () -> Unit
    ): Disposable = flowable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNextAction, onErrorAction, onCompletedAction)
            .also { subscriptions.add(it) }

    fun <T> untilDestroy(
            observable: Observable<T>,
            onNextAction: (T) -> Unit,
            onErrorAction: (Throwable) -> Unit,
            onCompletedAction: () -> Unit
    ): Disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNextAction, onErrorAction, onCompletedAction)
            .also { subscriptions.add(it) }

    fun <T> untilDestroy(
            single: Single<T>,
            onSuccessAction: (T) -> Unit,
            onErrorAction: (Throwable) -> Unit
    ): Disposable = single
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccessAction, onErrorAction)
            .also { subscriptions.add(it) }

    fun untilDestroy(
            completable: Completable,
            onCompletedAction: () -> Unit,
            onErrorAction: (Throwable) -> Unit
    ): Disposable = completable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onCompletedAction, onErrorAction)
            .also { subscriptions.add(it) }

    fun <T> untilDestroy(
            maybe: Maybe<T>,
            onSuccessAction: (T) -> Unit,
            onErrorAction: (Throwable) -> Unit,
            onCompletedAction: () -> Unit
    ): Disposable = maybe
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccessAction, onErrorAction, onCompletedAction)
            .also { subscriptions.add(it) }

    override fun onCleared() {
        isCreatedSubject.onNext(false)
        subscriptions.dispose()
    }

}

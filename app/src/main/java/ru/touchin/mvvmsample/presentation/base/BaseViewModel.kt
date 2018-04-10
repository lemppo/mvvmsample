package ru.touchin.mvvmsample.presentation.base

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.Functions
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel() : ViewModel() {

    private companion object {
        private val onErrorMissingConsumer: (Throwable) -> Unit = { Functions.ON_ERROR_MISSING.accept(it) }
    }

    private val isCreatedSubject = BehaviorSubject.createDefault<Boolean>(true)

    protected fun <T> untilDestroy(
            observable: Observable<T>,
            onNext: (T) -> Unit = { },
            onError: (Throwable) -> Unit = onErrorMissingConsumer,
            onComplete: () -> Unit = { }
    ): Disposable = innerUntilDestroy(observable, onNext, onError, onComplete)

    protected fun <T> untilDestroy(
            flowable: Flowable<T>,
            onNext: (T) -> Unit = { },
            onError: (Throwable) -> Unit = onErrorMissingConsumer,
            onComplete: () -> Unit = { }
    ): Disposable = innerUntilDestroy(flowable.toObservable(), onNext, onError, onComplete)

    protected fun <T> untilDestroy(
            single: Single<T>,
            onSuccess: (T) -> Unit = { },
            onError: (Throwable) -> Unit = onErrorMissingConsumer
    ): Disposable = innerUntilDestroy(single.toObservable(), onSuccess, onError, { })

    protected fun untilDestroy(
            completable: Completable,
            onComplete: () -> Unit = { },
            onError: (Throwable) -> Unit = onErrorMissingConsumer
    ): Disposable = innerUntilDestroy(completable.toObservable<Any>(), { }, onError, onComplete)

    protected fun <T> untilDestroy(
            maybe: Maybe<T>,
            onSuccess: (T) -> Unit = { },
            onError: (Throwable) -> Unit = onErrorMissingConsumer,
            onComplete: () -> Unit = { }
    ): Disposable = innerUntilDestroy(maybe.toObservable(), onSuccess, onError, onComplete)

    private fun <T> innerUntilDestroy(
            observable: Observable<T>, onNext: (T) -> Unit, onError: (Throwable) -> Unit, onCompleted: () -> Unit = { }): Disposable {
        val actualObservable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(onCompleted)
                .doOnNext(onNext)
                .doOnError(onError)
        return isCreatedSubject
                .firstOrError()
                .flatMapObservable { created -> if (created) actualObservable else Observable.empty() }
                .takeUntil(isCreatedSubject.filter { isCreated -> !isCreated })
                .onErrorResumeNext { throwable: Throwable ->
                    if (throwable is RuntimeException) {
                        // todo
//                        Lc.assertion(throwable)
                    }
                    Observable.empty<T>()
                }
                .subscribe()
    }

    override fun onCleared() {
        isCreatedSubject.onNext(false)
    }

}

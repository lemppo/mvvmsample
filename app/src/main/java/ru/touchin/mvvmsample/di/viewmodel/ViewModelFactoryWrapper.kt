package ru.touchin.mvvmsample.di.viewmodel

import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactoryWrapper {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
}
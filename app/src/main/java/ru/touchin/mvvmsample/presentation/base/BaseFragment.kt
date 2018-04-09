package ru.touchin.mvvmsample.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import ru.touchin.mvvmsample.MvvmSampleApp
import ru.touchin.mvvmsample.di.viewmodel.ViewModelFactoryWrapper

open class BaseFragment : Fragment() {
    private var viewModelFactoryWrapper = ViewModelFactoryWrapper()
    protected val viewModelFactory by lazy { viewModelFactoryWrapper.viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MvvmSampleApp.instance.applicationComponent.inject(viewModelFactoryWrapper)
        super.onViewCreated(view, savedInstanceState)
    }
}

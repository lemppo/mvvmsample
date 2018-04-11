package ru.touchin.mvvmsample.presentation.base

import android.support.v4.app.Fragment
import ru.touchin.mvvmsample.MvvmSampleApp

open class BaseFragment : Fragment() {
    protected val viewModelFactory by lazy { (requireActivity().application as MvvmSampleApp).viewModelFactory }
}

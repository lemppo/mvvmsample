package ru.touchin.mvvmsample.presentation.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.presentation.activities.MoviesActivity
import ru.touchin.mvvmsample.presentation.base.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(SplashViewModel::class.java)

        val observer = Observer<Boolean> {
            if (it == true) {
                requireActivity().startActivity(Intent(activity, MoviesActivity::class.java))
                requireActivity().finish()
            }
        }
        viewModel.ready.observe(this, observer)
    }
}
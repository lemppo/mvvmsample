package ru.touchin.mvvmsample.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.touchin.mvvmsample.MvvmSampleApp
import ru.touchin.mvvmsample.di.viewmodel.ViewModelFactoryWrapper

class MainActivity : AppCompatActivity() {

    private var viewModelFactoryWrapper = ViewModelFactoryWrapper()

    fun getViewModelFactory() = viewModelFactoryWrapper.viewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        MvvmSampleApp.instance.applicationComponent.inject(viewModelFactoryWrapper)
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SplashActivity::class.java))
    }

}

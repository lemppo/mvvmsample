package ru.touchin.mvvmsample.presentation.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.touchin.mvvmsample.presentation.splash.SplashFragment
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.presentation.base.FragmentNavigation

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
class SplashActivity : AppCompatActivity() {

    private val navigation = FragmentNavigation(this,
            supportFragmentManager,
            R.id.activity_splash_content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigation.setInitial(SplashFragment::class.java, null)
    }
}
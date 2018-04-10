package ru.touchin.mvvmsample.presentation.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.touchin.mvvmsample.presentation.movieslist.MoviesListFragment
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.presentation.base.FragmentNavigation

class MoviesActivity : AppCompatActivity() {

    val navigation = FragmentNavigation(this,
            supportFragmentManager,
            R.id.activity_movies_content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        if (savedInstanceState == null) {
            navigation.setInitial(MoviesListFragment::class.java, null)
        }
    }
}

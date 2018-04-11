package ru.touchin.mvvmsample.presentation.moviedetails

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.domain.moviedetails.MovieDetailsModel
import ru.touchin.mvvmsample.domain.moviedetails.MovieWrapper
import ru.touchin.mvvmsample.presentation.base.BaseFragment
import ru.touchin.mvvmsample.presentation.loadUri

class MovieDetailsFragment : BaseFragment() {

    companion object {
        fun args(movieId: Int) = Bundle().apply { putInt("movieId", movieId) }
    }

    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var progress: ContentLoadingProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)

        if (viewModel.movieDetailsModel.value == null && arguments != null) {
            viewModel.getMovie(arguments!!.getInt("movieId"))
        }

        (activity as AppCompatActivity).setSupportActionBar(view!!.findViewById(R.id.toolbar))

        progress = view!!.findViewById(R.id.view_controller_movie_details_progress)
        progress.visibility = View.VISIBLE

        viewModel.movieDetailsModel.observe(this, Observer {
            it?.let {
                when (it) {
                    is MovieDetailsModel.Error -> {
                        progress.visibility = View.GONE
                        renderError(it.error.message ?: getString(R.string.error_unknown))
                    }
                    is MovieDetailsModel.Movie -> {
                        progress.visibility = View.GONE
                        renderMovie(it.movie)
                    }
                }
            }
        })
    }

    private fun renderError(error: String) =
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()

    private fun renderMovie(wrapper: MovieWrapper) {
        (view!!.findViewById(R.id.ivPoster) as ImageView).loadUri(wrapper.imagePath)
        (view!!.findViewById<TextView>(R.id.tvRating)).text = wrapper.ratingString
        (view!!.findViewById<TextView>(R.id.tvDate)).text = wrapper.dateString
        (view!!.findViewById<TextView>(R.id.tvTitle)).text = wrapper.title
        (view!!.findViewById<Toolbar>(R.id.toolbar)).title = wrapper.title
        (view!!.findViewById<TextView>(R.id.tvDescription)).text = wrapper.overview
    }
}
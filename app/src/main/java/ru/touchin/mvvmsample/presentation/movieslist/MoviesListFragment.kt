package ru.touchin.mvvmsample.presentation.movieslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.*
import ru.touchin.kotlinsamples.data.database.Movie
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.data.MoviesRepository
import ru.touchin.mvvmsample.presentation.activities.MoviesActivity
import ru.touchin.mvvmsample.presentation.base.BaseFragment
import ru.touchin.mvvmsample.presentation.moviedetails.MovieDetailsFragment

class MoviesListFragment : BaseFragment() {

    private lateinit var viewModel: MoviesListViewModel

    private val prefs by lazy {
        context!!.getSharedPreferences(context!!.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE)
    }
    private val modeKey by lazy { context!!.getString(R.string.preference_mode_key) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MoviesListViewModel::class.java)

        (activity as AppCompatActivity).setSupportActionBar(view!!.findViewById(R.id.toolbar))
        setHasOptionsMenu(true)
        setMenuVisibility(true)

        val srMovies = view!!.findViewById<SwipeRefreshLayout>(R.id.refresh_movies)
        srMovies.setOnRefreshListener {
            viewModel.refreshMovies()
        }

        val adapter = MoviePagedAdapter()
        adapter.itemClickListener = { movie, _ ->
            (activity as MoviesActivity).navigation.push(MovieDetailsFragment::class.java,
                    MovieDetailsFragment.args(movie.id))
        }
        viewModel.pagedItems.observe(this, Observer<PagedList<Movie>> {
            adapter.submitList(it)
            srMovies.isRefreshing = false
        })

        val rvMovies: RecyclerView = view!!.findViewById(R.id.recycler_movies)
        rvMovies.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movies_list, menu)
        val mode = prefs.getInt(modeKey, MoviesRepository.POPULAR)
        when (mode) {
            MoviesRepository.POPULAR -> menu.findItem(R.id.actionShowPopular)?.isChecked = true
            MoviesRepository.TOP_RATED -> menu.findItem(R.id.actionShowTopRated)?.isChecked = true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when (item.itemId) {
            R.id.actionShowPopular -> viewModel.loadPopularMovies()
            R.id.actionShowTopRated -> viewModel.loadTopRatedMovies()
        }
        return super.onOptionsItemSelected(item)
    }
}
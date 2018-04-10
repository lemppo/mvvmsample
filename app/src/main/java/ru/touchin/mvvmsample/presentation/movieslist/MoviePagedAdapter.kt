package ru.touchin.mvvmsample.presentation.movieslist

import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.touchin.kotlinsamples.data.database.Movie
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.domain.global.models.ConfigurationRepository
import ru.touchin.mvvmsample.presentation.base.DelegatedPagedListAdapter
import ru.touchin.mvvmsample.presentation.base.adapter.ItemAdapterDelegate
import ru.touchin.mvvmsample.presentation.loadUri

class MoviePagedAdapter : DelegatedPagedListAdapter<Movie>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie?, newItem: Movie?): Boolean =
                    oldItem == newItem
        }

        private fun inflate(@LayoutRes layoutId: Int, parent: ViewGroup): View {
            return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }
    }

    init {
        addDelegate(MovieDelegate())
    }

    private inner class MovieDelegate : ItemAdapterDelegate<MovieViewHolder, Movie>() {
        override fun onBindViewHolder(holder: MovieViewHolder, item: Movie, adapterPosition: Int, collectionPosition: Int, payloads: MutableList<Any>) {
            // it`s here for boundary callback to work
            // because if getItem is not called, onItemAtEndLoaded is never called too
            getItem(collectionPosition)

            holder.onBindToItem(item)
        }

        override fun isForViewType(item: Any, positionInAdapter: Int, itemCollectionPosition: Int) = item is Movie

        override fun onCreateViewHolder(parent: ViewGroup) = MovieViewHolder(inflate(R.layout.item_movie, parent))
    }

    private inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMoviewPoster: ImageView = itemView.findViewById(R.id.imageview_movie_poster)!!

        fun onBindToItem(item: Movie) {
            ivMoviewPoster.loadUri(ConfigurationRepository.IMAGE_BASE_URL + item.poster_path)
        }
    }
}
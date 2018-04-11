package ru.touchin.mvvmsample.presentation.base

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.touchin.mvvmsample.presentation.base.adapter.AdapterDelegate
import ru.touchin.mvvmsample.presentation.base.adapter.DelegatesManager

/**
 * Base adapter with delegation and diff computing on background thread.
 */
open class DelegatedPagedListAdapter<TItem>(config: AsyncDifferConfig<TItem>) : PagedListAdapter<TItem, RecyclerView.ViewHolder>(config) {

    constructor(diffCallback: DiffUtil.ItemCallback<TItem>) : this(AsyncDifferConfig.Builder<TItem>(diffCallback).build())

    var itemClickListener: ((TItem, RecyclerView.ViewHolder) -> Unit)? = null

    private val delegatesManager = DelegatesManager()

    open fun getHeadersCount() = 0

    open fun getFootersCount() = 0

    override fun getItemCount() = getHeadersCount() + super.getItemCount() + getFootersCount()

    override fun getItemViewType(position: Int) = delegatesManager.getItemViewType(currentList!!, position, getCollectionPosition(position))

    override fun getItemId(position: Int) = delegatesManager.getItemId(currentList!!, position, getCollectionPosition(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        val collectionPosition = getCollectionPosition(position)
        if (collectionPosition in 0 until currentList!!.size) {
            if (itemClickListener != null) {
                holder.itemView.setOnClickListener { itemClickListener?.invoke(currentList!![collectionPosition]!!, holder) }
            } else {
                holder.itemView.setOnClickListener(null)
            }
        }
        delegatesManager.onBindViewHolder(holder, currentList!!, position, collectionPosition, payloads)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit

    /**
     * Adds [AdapterDelegate] to adapter.
     *
     * @param delegate Delegate to add.
     */
    fun addDelegate(delegate: AdapterDelegate<*>) = delegatesManager.addDelegate(delegate)

    /**
     * Removes [AdapterDelegate] from adapter.
     *
     * @param delegate Delegate to remove.
     */
    fun removeDelegate(delegate: AdapterDelegate<*>) = delegatesManager.removeDelegate(delegate)

    fun getCollectionPosition(adapterPosition: Int) = adapterPosition - getHeadersCount()
}
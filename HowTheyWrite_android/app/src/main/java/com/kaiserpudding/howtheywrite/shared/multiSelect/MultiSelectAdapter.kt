package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class MultiSelectAdapter<T>(
        private val listener: MultiSelectAdapterItemInteractionListener)
    : RecyclerView.Adapter<MultiSelectAdapter<T>.MultiSelectViewHolder>() {

    private val mutableSelectedId: MutableSet<Int> = mutableSetOf()
    val selectedId: IntArray
        get() = mutableSelectedId.toIntArray()
    private val mutableInSelectionMode = MutableLiveData<Boolean>()
    val inSelectionMode: LiveData<Boolean>
        get() = mutableInSelectionMode
    private val mutableNumberOfSelected = MutableLiveData<Int>()
    val numberOfSelected: LiveData<Int>
        get() = mutableNumberOfSelected
    protected abstract val viewHolderId: Int
    protected var list: List<T>? = null

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        holder.itemView.isActivated = selectedId.contains(getMyItemId(position))
    }

    fun toggleSelectedThenNotify(id: Int) {
        if (selectedId.contains(id)) mutableSelectedId.remove(id)
        else mutableSelectedId.add(id)
        mutableInSelectionMode.postValue(selectedId.isNotEmpty())
        mutableNumberOfSelected.postValue(selectedId.size)
        notifyDataSetChanged()
    }

    fun clearSelectedThenNotify() {
        mutableSelectedId.clear()
        mutableInSelectionMode.postValue(false)
        notifyDataSetChanged()
    }

    protected abstract fun getMyItemId(position: Int): Int

    protected fun createViewHolder(view: View) : MultiSelectViewHolder {
        return MultiSelectViewHolder(view)
    }

    inner class MultiSelectViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(viewHolderId)

        init {
            view.setOnClickListener {
                listener.onMultiSelectAdapterInteraction(getMyItemId(adapterPosition))
            }
            view.setOnLongClickListener {
                listener.onMultiSelectAdapterLongInteraction(getMyItemId(adapterPosition)); true }
        }
    }

    interface MultiSelectAdapterItemInteractionListener {
        fun onMultiSelectAdapterInteraction(itemId: Int)
        fun onMultiSelectAdapterLongInteraction(itemId: Int)
    }
}
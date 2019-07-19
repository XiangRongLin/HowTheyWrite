package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectAdapter.MultiSelectAdapterItemInteractionListener

/**
 * An adapter handling the logic needed for a recyclerView with clickable and selectable item.
 * Observe [inSelectionMode] and [numberOfSelected]
 *
 * @param T They type of the item shown in the list
 * @property listener A listener implementing [MultiSelectAdapterItemInteractionListener]
 * handling onClick and onLongClick actions on the items
 */
abstract class MultiSelectAdapter<T>(
        private val listener: MultiSelectAdapterItemInteractionListener)
    : RecyclerView.Adapter<MultiSelectAdapter<T>.MultiSelectViewHolder>() {

    private val selectedIdSet = mutableSetOf<Int>()
    val selectedIdArray: IntArray
        get() = selectedIdSet.toIntArray()
    val numberOfSelected: Int
        get() = selectedIdArray.size
    val inSelectionMode: Boolean
        get() = numberOfSelected > 0
    protected abstract val viewHolderId: Int
    protected var list: List<T>? = null

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        holder.itemView.isActivated = selectedIdSet.contains(getMyItemId(position))
    }


    fun toggleSelectedThenNotify(id: Int) {
        if (selectedIdSet.contains(id)) selectedIdSet.remove(id)
        else selectedIdSet.add(id)
        listener.onDataSetChanged()
        notifyDataSetChanged()
    }

    fun clearSelectedThenNotify() {
        selectedIdSet.clear()
        listener.onDataSetChanged()
        notifyDataSetChanged()
    }

    protected abstract fun getMyItemId(position: Int): Int

    protected fun createViewHolder(view: View): MultiSelectViewHolder {
        return MultiSelectViewHolder(view)
    }

    inner class MultiSelectViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(viewHolderId)

        init {
            view.setOnClickListener {
                listener.onMultiSelectAdapterInteraction(getMyItemId(adapterPosition))
            }
            view.setOnLongClickListener {
                listener.onMultiSelectAdapterLongInteraction(getMyItemId(adapterPosition)); true
            }
        }
    }

    interface MultiSelectAdapterItemInteractionListener {
        fun onDataSetChanged()
        fun onMultiSelectAdapterInteraction(itemId: Int)
        fun onMultiSelectAdapterLongInteraction(itemId: Int)
    }
}

package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectAdapter.MultiSelectAdapterItemInteractionListener
import com.kaiserpudding.howtheywrite.shared.setSafeOnClickListener

/**
 * An adapter handling the logic needed for a recycler view with clickable and selectable items.
 *
 * @param T They type of the items shown in the list
 * @property listener A listenerBase implementing [MultiSelectAdapterItemInteractionListener]
 * handling onClick and onLongClick actions on the items
 */
abstract class MultiSelectAdapter<T>(
        private val listener: MultiSelectAdapterItemInteractionListener)
    : RecyclerView.Adapter<MultiSelectAdapter<T>.MultiSelectViewHolder>() {

    private val selectedIdSet = mutableSetOf<Long>()
    val selectedIdArray: LongArray
        get() = selectedIdSet.toLongArray()
    val numberOfSelected: Int
        get() = selectedIdArray.size
    val inSelectionMode: Boolean
        get() = numberOfSelected > 0

    /**
     * The id of the layout that is representing a single item in the recycler view
     */
    protected abstract val viewHolderId: Int

    /**
     * The list of the items that are represented in the recycler view
     */
    var list: List<T>? = null


    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        //if item at position is selected, activate it and thus show different background color
        holder.itemView.isActivated = selectedIdSet.contains(getMyItemId(position))
    }

    /**
     * Remove/Add the id of the item at [position] from [selectedIdSet] depending on if its already in there.
     * Then notifies listeners of changes.
     *
     * @param position
     */
    fun toggleSelectedThenNotify(position: Int) {
        val id = getMyItemId(position)
        if (selectedIdSet.contains(id)) selectedIdSet.remove(id)
        else selectedIdSet.add(id)
        listener.onDataSetChanged()
        notifyItemChanged(position)
    }

    /**
     * Clear [selectedIdSet] and then notifies listeners of changes.
     *
     */
    fun clearSelectedThenNotify() {
        selectedIdSet.clear()
        listener.onDataSetChanged()
        notifyDataSetChanged()
    }

    /**
     * Return the id of the item at [position]
     *
     * @param position
     * @return The id of the item
     */
    protected abstract fun getMyItemId(position: Int): Long

    /**
     * Method to create a [MultiSelectViewHolder].
     * Used to avoid the inner class restriction.
     *
     * @param view
     * @return
     */
    protected fun createViewHolder(view: View): MultiSelectViewHolder {
        return MultiSelectViewHolder(view)
    }

    inner class MultiSelectViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(viewHolderId)

        init {
            view.setSafeOnClickListener {
                if (inSelectionMode) toggleSelectedThenNotify(adapterPosition)
                else listener.onMultiSelectAdapterInteraction(getMyItemId(adapterPosition))
            }
            view.setOnLongClickListener {
                toggleSelectedThenNotify(adapterPosition); true
            }
        }
    }

    /**
     * Interface which must be implemented to get notified of data changes and adapter interaction
     *
     */
    interface MultiSelectAdapterItemInteractionListener {

        /**
         * Gets called when an item was selected or deselected.
         */
        fun onDataSetChanged()

        /**
         * Gets called when an item was clicked on while adapter is not [inSelectionMode]
         *
         * @param itemId The id of the item that was clicked.
         */
        fun onMultiSelectAdapterInteraction(itemId: Long)
    }
}

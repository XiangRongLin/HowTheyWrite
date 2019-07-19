package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.view.ActionMode
import androidx.fragment.app.Fragment

/**
 * A Fragment handling the logic needed for a recycler view with clickable and selectable items.
 *
 * Implements the [MultiSelectAdapter.MultiSelectAdapterItemInteractionListener] and basic things
 * of [ActionMode] to display a bar at the top when something is selected.
 *
 * @param T The type of the items shown in the list.
 */
abstract class MultiSelectFragment<T> : Fragment(),
        MultiSelectAdapter.MultiSelectAdapterItemInteractionListener {

    protected lateinit var adapter: MultiSelectAdapter<T>
    protected abstract val actionModeCallback: ActionMode.Callback
    protected var actionMode: ActionMode? = null

    override fun onStop() {
        super.onStop()
        actionMode?.finish()
        adapter.clearSelectedThenNotify()
    }

    protected abstract fun onListInteraction(itemId: Int)

    /**
     * Start/Stop/Continue action mode depending on if [MultiSelectAdapter.inSelectionMode]
     * and update [ActionMode.setTitle] if action mode is still active.
     */
    override fun onDataSetChanged() {
        if (adapter.inSelectionMode) {
            if (actionMode == null) actionMode = activity?.startActionMode(actionModeCallback)
            actionMode?.title = "${adapter.numberOfSelected} selected"
        } else {
            actionMode?.finish()
        }
    }

    override fun onMultiSelectAdapterInteraction(itemId: Int) {
        onListInteraction(itemId)
    }
}
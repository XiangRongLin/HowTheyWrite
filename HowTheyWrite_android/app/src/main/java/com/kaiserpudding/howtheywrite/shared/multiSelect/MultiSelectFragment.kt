package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
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
    protected var actionMode: ActionMode? = null

    /**
     * The id of the menu shown in the [ActionMode].
     */
    protected abstract val actionMenuId: Int

    /**
     * [ActionMode.Callback] implementation which relies on subclasses implementing
     * [actionMenuId] and [onMyActionItemClicked].
     */
    private val actionModeCallback: ActionMode.Callback =
            object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                    val inflater = mode.menuInflater
                    inflater.inflate(actionMenuId, menu)
                    mode.title = "${adapter.numberOfSelected} selected"
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                    return onMyActionItemClicked(mode, item)
                }

                override fun onDestroyActionMode(mode: ActionMode) {
                    adapter.clearSelectedThenNotify()
                    actionMode = null
                }
            }

    /**
     * Implementation for the [ActionMode.Callback.onActionItemClicked] method.
     * Needs to handle click on items of menu with [actionMenuId].
     *
     * @param mode The current ActionMode
     * @param item The item that was clicked
     * @return true if this callback handled the event, false if the standard MenuItem invocation should continue.
     */
    protected abstract fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean

    override fun onStop() {
        super.onStop()
        actionMode?.finish()
        adapter.clearSelectedThenNotify()
    }

    protected abstract fun onListInteraction(itemId: Long)

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

    override fun onMultiSelectAdapterInteraction(itemId: Long) {
        onListInteraction(itemId)
    }
}
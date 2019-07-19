package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.view.ActionMode
import androidx.fragment.app.Fragment

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

    override fun onDataSetChanged() {
        if (adapter.inSelectionMode) {
            if (actionMode == null) actionMode = activity?.startActionMode(actionModeCallback)
            actionMode?.title = "${adapter.numberOfSelected} selected"
        } else {
            actionMode?.finish()
        }
    }

    override fun onMultiSelectAdapterInteraction(itemId: Int) {
        if (adapter.inSelectionMode) adapter.toggleSelectedThenNotify(itemId)
        else (onListInteraction(itemId))
    }

    override fun onMultiSelectAdapterLongInteraction(itemId: Int) {
        adapter.toggleSelectedThenNotify(itemId)
    }
}
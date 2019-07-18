package com.kaiserpudding.howtheywrite.shared.multiSelect

import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class MultiSelectFragment<T> : Fragment(),
        MultiSelectAdapter.MultiSelectAdapterItemInteractionListener{

    protected lateinit var adapter: MultiSelectAdapter<T>
    protected abstract val actionModeCallback: ActionMode.Callback
    protected var actionMode: ActionMode? = null
    protected var inSelectionMode = false
    protected val selectedNumber
        get() = adapter.selectedId.size

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeAdapter()
    }

    override fun onStop() {
        super.onStop()
        actionMode?.finish()
        adapter.clearSelectedThenNotify()
    }

    private fun observeAdapter() {
        adapter.inSelectionMode.observe(this, Observer {
            this.inSelectionMode = it
            if (inSelectionMode && actionMode == null) actionMode = activity?.startActionMode(actionModeCallback)
            else if (!inSelectionMode) actionMode?.finish()
        })
        adapter.numberOfSelected.observe(this, Observer {
            actionMode?.title = "$it selected"
        })
    }

    protected abstract fun onListInteraction(itemId: Int)

    override fun onMultiSelectAdapterInteraction(itemId: Int) {
        if (inSelectionMode) adapter.toggleSelectedThenNotify(itemId)
        else (onListInteraction(itemId))
    }

    override fun onMultiSelectAdapterLongInteraction(itemId: Int) {
        adapter.toggleSelectedThenNotify(itemId)
    }
}
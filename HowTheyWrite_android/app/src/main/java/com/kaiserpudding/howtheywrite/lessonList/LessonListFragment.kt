package com.kaiserpudding.howtheywrite.lessonList

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.shared.ConfirmationDialogFragment
import com.kaiserpudding.howtheywrite.shared.setSafeOnClickListener

class LessonListFragment
    : BaseLessonListFragment(), ConfirmationDialogFragment.ConfirmationDialogListener {

    override val type = BaseLessonListType.LESSON_LIST

    override val actionMenuId: Int
        get() = R.menu.selection_delete_menu

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showConfirmationDialog()
                true
            }
            else -> false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val fab = view.findViewById<FloatingActionButton>(R.id.new_lessen_fab)
        fab.setSafeOnClickListener {
            onNewLessonButtonPressed()
        }
        return view
    }

    private fun onNewLessonButtonPressed() {
        listenerBase?.onToNewLessonInteraction()
    }

    override fun onListInteraction(itemId: Long) {
        listenerBase?.onBaseLessonListItemInteraction(itemId, type)
    }

    private fun showConfirmationDialog() {
        val dialog = ConfirmationDialogFragment(this)
        dialog.show(childFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick() {
        if (adapter.selectedIdArray.contains(0)) {
            val toast = Toast.makeText(context, "The All lesson can't be deleted", Toast.LENGTH_LONG)
            toast.show()
        }
        lessonListViewModel.deleteLessons(adapter.selectedIdArray)
        actionMode?.finish()
    }

    override fun onDialogNegativeClick() {
        val toast = Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
        toast.show()
    }
}

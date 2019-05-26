package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.shared.ConfirmationDialogFragment

/**
 * A simple [Fragment] subclass.
 *
 * It contains a recyclerView which displays all characters for a given lessonId.
 *
 * Shows all characters if no lessonId is given.
 *
 * Activities that contain this fragment must implement the
 * [CharacterListFragment.OnCharacterListFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class CharacterListFragment
    : BaseCharacterListFragment(),
    ConfirmationDialogFragment.ConfirmationDialogListener{

    override val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.selection_delete_menu, menu)
            mode.title = "${++selectedNumber} selected"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_delete -> {
                    showConfirmationDialog()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            adapter.clearSelected()
            selectedNumber = 0
            actionMode = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //to make it immutable
        val tmpId = lessonId
        //instantiate the viewModel
        characterListViewModel =
                ViewModelProviders.of(
                        this, CharacterListViewModelFactory(activity!!.application, tmpId, false))
                        .get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val newCharacterFab = view.findViewById<FloatingActionButton>(R.id.new_character_fab)
        newCharacterFab.setOnClickListener { onToNewCharacterFabPressed() }

        val toQuizButton = view.findViewById<Button>(R.id.to_quiz_button)
        toQuizButton.setOnClickListener { onToQuizButtonPressed() }

        return view
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle(lessonName)
    }

    private fun onToQuizButtonPressed() {
        if (inSelectionMode) listener?.onToQuizInteraction(adapter.selectedCharacterId, lessonName)
        else listener?.onToQuizInteraction(lessonId, lessonName)
    }

    private fun showConfirmationDialog() {
        val dialog = ConfirmationDialogFragment(this)
        dialog.show(childFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick() {
        characterListViewModel.deleteCharactersFromLesson(adapter.selectedCharacterId)
        actionMode?.finish()
    }

    override fun onDialogNegativeClick() {
        val toast = Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
        toast.show()
    }
}

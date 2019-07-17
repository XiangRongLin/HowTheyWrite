package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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
        ConfirmationDialogFragment.ConfirmationDialogListener {

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
            adapter.clearSelectedThenNotify()
            selectedNumber = 0
            actionMode = null
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //to make it immutable
        val tmpId = lessonId
        characterListViewModel = ViewModelProviders.of(
                this, CharacterListViewModelFactory(activity!!.application, tmpId, false))
                .get(CharacterListViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val toQuizButton = view.findViewById<Button>(R.id.to_quiz_button)
        toQuizButton.setOnClickListener { onToQuizButtonPressed() }

        return view
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle(lessonName)
    }

    private fun onToQuizButtonPressed() {
        if (inSelectionMode) listener?.onToQuizInteraction(adapter.selectedId, lessonName)
        else listener?.onToQuizInteraction(lessonId, lessonName)
    }

    private fun showConfirmationDialog() {
        val dialog = ConfirmationDialogFragment(this)
        dialog.show(childFragmentManager, "dialog")
    }

    private fun onAddToLessonPressed() {
        listener?.onAddToLessonInteraction(lessonId, lessonName)
    }

    override fun onDialogPositiveClick() {
        characterListViewModel.deleteCharactersFromLesson(adapter.selectedId)
        actionMode?.finish()
    }

    override fun onDialogNegativeClick() {
        val toast = Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.character_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new_character -> {
                onToNewCharacterPressed()
                true
            }
            R.id.add_to_lesson -> {
                onAddToLessonPressed()
                true
            }
            else -> false
        }
    }

    override fun onCharacterListInteraction(characterId: Int) {
        listener?.onCharacterListItemInteraction(characterId, 0)
    }
}

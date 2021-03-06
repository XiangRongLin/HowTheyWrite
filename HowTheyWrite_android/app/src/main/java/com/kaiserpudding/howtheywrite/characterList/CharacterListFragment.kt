package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.shared.ConfirmationDialogFragment
import com.kaiserpudding.howtheywrite.shared.setSafeOnClickListener

/**
 * A simple [Fragment] subclass.
 *
 * It contains a recyclerView which displays all characters for a given lessonId.
 *
 * Shows all characters if no lessonId is given.
 *
 * Activities that contain this fragment must implement the
 * [BaseCharacterListFragment.OnCharacterListFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class CharacterListFragment
    : BaseCharacterListFragment(),
        ConfirmationDialogFragment.ConfirmationDialogListener {

    private val type = BaseCharacterListType.CHARACTER_LIST
    override val actionMenuId = R.menu.selection_delete_menu

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showConfirmationDialog()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.character_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_new_character -> {
                listener?.onNewCharacterInteraction(lessonId, type)
                true
            }
            R.id.action_add_to_lesson -> {
                listener?.onAddToLessonInteraction(lessonId, lessonName)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //to make it immutable
        val tmpId = lessonId
        characterListViewModel = ViewModelProviders.of(
                this, CharacterListViewModelFactory(activity!!.application, tmpId))
                .get(CharacterListViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val toQuizButton = view.findViewById<Button>(R.id.to_quiz_button)
        toQuizButton.setSafeOnClickListener { onToQuizButtonPressed() }
        return view
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle(lessonName)
    }

    private fun onToQuizButtonPressed() {
        when {
            adapter.list == null -> {
                val toast = Toast.makeText(context, "Please wait for it to finish loading", Toast.LENGTH_SHORT)
                toast.show()
            }
            adapter.list!!.isEmpty() -> {
                val toast = Toast.makeText(context, "This lesson doesn't contain any characters", Toast.LENGTH_SHORT)
                toast.show()
            }
            adapter.inSelectionMode -> listener?.onToQuizInteraction(adapter.selectedIdArray, lessonName)
            else -> listener?.onToQuizInteraction(lessonId, lessonName)
        }
    }

    private fun showConfirmationDialog() {
        val dialog = ConfirmationDialogFragment(this)
        dialog.show(childFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick() {
        characterListViewModel.deleteCharactersFromLesson(adapter.selectedIdArray)
        actionMode?.finish()
    }

    override fun onDialogNegativeClick() {
        val toast = Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onListInteraction(itemId: Long) {
        listener?.onCharacterListItemInteraction(itemId, BaseCharacterListType.CHARACTER_LIST)
    }
}

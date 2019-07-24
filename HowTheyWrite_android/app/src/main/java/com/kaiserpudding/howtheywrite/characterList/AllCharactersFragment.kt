package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.shared.ConfirmationDialogFragment

class AllCharactersFragment : BaseCharacterListFragment(),
        ConfirmationDialogFragment.ConfirmationDialogListener {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_character_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new_character -> {
                onToNewCharacterPressed(ALL_CHARACTERS_TYPE)
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterListViewModel = ViewModelProviders.of(
                this, CharacterListViewModelFactory(activity!!.application, -1, true))
                .get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        view.findViewById<Button>(R.id.to_quiz_button).visibility = View.GONE

        return view
    }

    private fun showConfirmationDialog() {
        val dialog = ConfirmationDialogFragment(this)
        dialog.show(childFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick() {
        characterListViewModel.deleteCharacters(adapter.selectedIdArray)
        actionMode?.finish()
    }

    override fun onDialogNegativeClick() {
        val toast = Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle("All Characters")
    }

    override fun onListInteraction(itemId: Long) {
        listener?.onCharacterListItemInteraction(itemId, 2)
    }
}
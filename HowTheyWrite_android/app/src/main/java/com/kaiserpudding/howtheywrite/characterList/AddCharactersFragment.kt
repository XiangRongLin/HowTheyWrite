package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.kaiserpudding.howtheywrite.R

class AddCharactersFragment : BaseCharacterListFragment() {

    override val actionMenuId = R.menu.selection_add_menu

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_confirm -> {
                characterListViewModel.addCharactersToLesson(adapter.selectedIdArray)
                mode.finish()
                listener?.onFinish()
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterListViewModel =
                ViewModelProviders.of(
                        this, CharacterListViewModelFactory(activity!!.application, lessonId, true))
                        .get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        view.findViewById<Button>(R.id.to_quiz_button).visibility = View.GONE

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle("Add to $lessonName")
    }

    override fun onListInteraction(itemId: Long) {
        listener?.onCharacterListItemInteraction(itemId, BaseCharacterListType.ADD_CHARACTER)
    }
}
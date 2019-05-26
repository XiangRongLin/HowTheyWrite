package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R

class AddCharactersFragment : BaseCharacterListFragment() {

    override val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.selection_add_menu, menu)
            mode.title = "${++selectedNumber} selected"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_confirm -> {
                    characterListViewModel.addCharactersToLesson(adapter.selectedCharacterId)
                    mode.finish()
                    listener?.onFinish()
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

        characterListViewModel =
                ViewModelProviders.of(
                        this, CharacterListViewModelFactory(activity!!.application, lessonId, true))
                        .get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.new_character_fab).visibility = View.GONE
        view.findViewById<Button>(R.id.to_quiz_button).visibility = View.GONE

        return view
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle("Add to $lessonName")
    }
}
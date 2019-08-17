package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kaiserpudding.howtheywrite.R
import java.lang.RuntimeException

class AddCharactersFragment : BaseCharacterListFragment() {

    override val actionMenuId = R.menu.selection_add_menu
    private var addToId = -1L
    private lateinit var addToName: String
    private var listenerSecond: OnAddCharacterFragmentInteractionListener? = null

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_confirm -> {
                characterListViewModel.addCharactersToLesson(adapter.selectedIdArray, addToId)
                mode.finish()
                listenerSecond?.onAddFinish()
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val safeArgs: AddCharactersFragmentArgs by navArgs()
        addToId = safeArgs.addToId
        addToName = safeArgs.addToName
        characterListViewModel =
                ViewModelProviders.of(
                        this, CharacterListViewModelFactory(activity!!.application, lessonId))
                        .get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view.findViewById<Button>(R.id.to_quiz_button).visibility = View.GONE
        return view
    }

    override fun updateToolBarTitle() {
        listener?.updateTitle("Add to $addToName")
    }

    override fun onListInteraction(itemId: Long) {
        listener?.onCharacterListItemInteraction(itemId, BaseCharacterListType.ADD_CHARACTER)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddCharacterFragmentInteractionListener) {
            listenerSecond = context
        } else {
            throw RuntimeException("$context must implement OnAddCharacterFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerSecond = null
    }

    interface OnAddCharacterFragmentInteractionListener {
        fun onAddFinish()
    }
}
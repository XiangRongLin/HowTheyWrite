package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R

abstract class BaseCharacterListFragment : Fragment(), CharacterListAdapter.OnCharacterListAdapterItemInteractionListener {
    protected var lessonId: Int = -1
    protected lateinit var lessonName: String
    protected var listener: OnCharacterListFragmentInteractionListener? = null
    protected lateinit var characterListViewModel: CharacterListViewModel
    protected lateinit var adapter: CharacterListAdapter
    protected var inSelectionMode: Boolean = false
    protected var actionMode: ActionMode? = null
    protected var selectedNumber = 0

    protected abstract val actionModeCallback: ActionMode.Callback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs: CharacterListFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
        lessonName = safeArgs.lessonName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)

        //instantiate the recyclerView with its adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.character_recyclerview)
        adapter = CharacterListAdapter(view.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(view.context, 5)

        adapter.inSelectionMode.observe(this, Observer { inSelectionMode ->
            this.inSelectionMode = inSelectionMode
            if (inSelectionMode && actionMode == null) actionMode = activity?.startActionMode(actionModeCallback)
            else if (!inSelectionMode) actionMode?.finish()
        })

        adapter.numberOfSelected.observe(this, Observer {

            actionMode?.title = "$it selected"
        })

        //add observer to viewModel to set characters into the adapter
        characterListViewModel.characters.observe(this,
                Observer { characters ->
                    adapter.setCharacters(characters)
                }
        )

        updateToolBarTitle()

        return view
    }

    override fun onStop() {
        super.onStop()
        actionMode?.finish()
        adapter.clearSelectedThenNotify()
    }

    protected abstract fun updateToolBarTitle()

    override fun onCharacterListAdapterInteraction(characterId: Int) {
        if (inSelectionMode) adapter.toggleSelectedThenNotify(characterId)
        else onCharacterListInteraction(characterId)
    }

    protected abstract fun onCharacterListInteraction(characterId: Int)

    /**
     * TODO
     *
     * @param characterId
     */
    override fun onCharacterListAdapterLongInteraction(characterId: Int) {
        adapter.toggleSelectedThenNotify(characterId)
    }


    protected fun onToNewCharacterPressed() {
        listener?.onNewCharacterInteraction(lessonId, lessonName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCharacterListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnCharacterListFragmentInteractionListener {
        fun onToQuizInteraction(lessonId: Int, lessonName: String)
        fun onToQuizInteraction(characterIds: IntArray, lessonName: String)
        /**
         * @param characterId
         * @param type Specifies from which specialized fragment it was called. 0 for CharacterList, 1 for AddCharacters
         */
        fun onCharacterListItemInteraction(characterId: Int, type: Int)

        fun onNewCharacterInteraction(lessonId: Int, lessonName: String)
        fun onAddToLessonInteraction(lessonId: Int, lessonName: String)
        fun updateTitle(title: String)
        fun onFinish()
    }
}
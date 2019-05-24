package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R

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
    : Fragment(), CharacterListAdapter.OnCharacterListAdapterItemInteractionListener {
    private var lessonId: Int = -1
    private lateinit var lessonName: String
    private var listener: OnCharacterListFragmentInteractionListener? = null
    private lateinit var characterListViewModel: CharacterListViewModel
    private lateinit var adapter: CharacterListAdapter
    private var inSelectionMode: Boolean = false
    private var actionMode: ActionMode? = null
    private var selectedNumber = 0

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.selection_menu, menu)
            mode.title = "${++selectedNumber} selected"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            //TODO handle delete button
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            adapter.clearSelected()
            selectedNumber = 0
            actionMode = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //default value is -1
        val safeArgs: CharacterListFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
        lessonName = safeArgs.lessonName

        //to make it immutable
        val tmpId = lessonId
        //instantiate the viewModel
        characterListViewModel =
                ViewModelProviders.of(
                        this, CharacterListViewModelFactory(activity!!.application, tmpId))
                        .get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)

        //instantiate the recyclerView with its adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.character_recyclerview)
        adapter = CharacterListAdapter(view.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(view.context, 5)

        adapter.inSelectionMode.observe(this, Observer {inSelectionMode ->
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

        val newCharacterFab = view.findViewById<FloatingActionButton>(R.id.new_character_fab)
        newCharacterFab.setOnClickListener { onToNewCharacterFabPressed() }

        val toQuizButton = view.findViewById<Button>(R.id.to_quiz_button)
        toQuizButton.setOnClickListener { onToQuizButtonPressed() }

        updateToolBarTitle()

        return view
    }

    override fun onStop() {
        super.onStop()
        actionMode?.finish()
        adapter.clearSelected()
    }

    private fun updateToolBarTitle() {
        listener?.updateTitle(lessonName)
    }

    private fun onToQuizButtonPressed() {
        listener?.onToQuizInteraction(lessonId, lessonName)
    }

    override fun onCharacterListAdapterInteraction(characterId: Int) {
        if (inSelectionMode) adapter.toggleSelected(characterId)
        else listener?.onCharacterListItemInteraction(characterId)
    }

    /**
     * TODO
     *
     * @param characterId
     */
    override fun onCharacterListAdapterLongInteraction(characterId: Int) {
        adapter.toggleSelected(characterId)
    }


    private fun onToNewCharacterFabPressed() {
        listener?.onNewCharacterInteraction()
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
        fun onCharacterListItemInteraction(characterId: Int)
        fun onNewCharacterInteraction()
        fun updateTitle(title: String)
    }
}

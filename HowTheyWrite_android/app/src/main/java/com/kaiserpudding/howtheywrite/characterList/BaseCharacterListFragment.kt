package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectFragment

abstract class BaseCharacterListFragment : MultiSelectFragment<Character>() {
    protected var lessonId: Long = -1
    protected lateinit var lessonName: String
    protected var listener: OnCharacterListFragmentInteractionListener? = null
    protected lateinit var characterListViewModel: CharacterListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs: CharacterListFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
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

        //add observer to viewModel to set characters into the adapter
        characterListViewModel.characters.observe(this,
                Observer { characters ->
                    (adapter as CharacterListAdapter).setCharacters(characters)
                }
        )
        characterListViewModel.lessonName.observe(this,
                Observer {
                    lessonName = it
                    updateToolBarTitle()
                })

        return view
    }

    override fun onPause() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        super.onPause()
    }


    protected abstract fun updateToolBarTitle()

    protected fun onToNewCharacterPressed(type: Int) {
        listener?.onNewCharacterInteraction(lessonId, lessonName, type)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (adapter as CharacterListAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (adapter as CharacterListAdapter).filter.filter(newText)
                return false
            }

        })
        searchView.setOnCloseListener {
            (adapter as CharacterListAdapter).resetCharacters()
            false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        const val CHARACTER_LIST_TYPE = 0
        const val ADD_CHARACTERS_TYPE = 1
        const val ALL_CHARACTERS_TYPE = 2
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnCharacterListFragmentInteractionListener {
        fun onToQuizInteraction(lessonId: Long, lessonName: String)
        fun onToQuizInteraction(characterIds: LongArray, lessonName: String)
        /**
         * @param characterId
         * @param type Specifies from which specialized fragment it was called. 0 for CharacterList, 1 for AddCharacters
         */
        fun onCharacterListItemInteraction(characterId: Long, type: Int)

        fun onNewCharacterInteraction(lessonId: Long, lessonName: String, type: Int)
        fun onAddToLessonInteraction(lessonId: Long, lessonName: String)
        fun updateTitle(title: String)
        fun onFinish()
    }
}
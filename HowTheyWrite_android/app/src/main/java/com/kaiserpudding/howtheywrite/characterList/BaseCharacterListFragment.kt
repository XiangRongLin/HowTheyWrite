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

        setHasOptionsMenu(true)

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
        inflater.inflate(R.menu.search_menu, menu)

        val filterMenuItem = menu.findItem(R.id.action_filter)
        val searchMenuItem = menu.findItem(R.id.action_search)

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                filterMenuItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                filterMenuItem.isVisible = false

                /**
                 * Workaround for disappearing menu items when collapsing an expandable item like a SearchView.
                 * This method should be removed when fixed upstream.
                 * Issue link: https://issuetracker.google.com/issues/37657375
                 */
                activity?.invalidateOptionsMenu()
                return true
            }
        })

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                when ((adapter as CharacterListAdapter).filterType) {
                    ALL_FILTER -> item.subMenu.getItem(0).isChecked = true
                    HANZI_FILTER -> item.subMenu.getItem(1).isChecked = true
                    PINYIN_FILTER -> item.subMenu.getItem(2).isChecked = true
                    TRANSLATION_FILTER -> item.subMenu.getItem(3).isChecked = true
                }
                false
            }
            R.id.filter_all -> {
                (adapter as CharacterListAdapter).filterType = ALL_FILTER
                item.isChecked = true
                true
            }
            R.id.filter_hanzi -> {
                (adapter as CharacterListAdapter).filterType = HANZI_FILTER
                item.isChecked = true
                true
            }
            R.id.filter_pinyin -> {
                (adapter as CharacterListAdapter).filterType = PINYIN_FILTER
                item.isChecked = true
                true
            }
            R.id.filter_translation -> {
                (adapter as CharacterListAdapter).filterType = TRANSLATION_FILTER
                item.isChecked = true
                true
            }
            else -> false
        }
    }

    companion object {
        const val CHARACTER_LIST_TYPE = 0
        const val ADD_CHARACTERS_TYPE = 1
        const val ALL_CHARACTERS_TYPE = 2
        const val ALL_FILTER = "all_filter"
        const val HANZI_FILTER = "hanzi_filter"
        const val PINYIN_FILTER = "pinyin_filter"
        const val TRANSLATION_FILTER = "translation_filter"
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
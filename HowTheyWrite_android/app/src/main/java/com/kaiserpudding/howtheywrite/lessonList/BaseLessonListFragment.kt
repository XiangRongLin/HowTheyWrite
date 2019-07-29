package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectFragment


/**
 *
 * It contains a recyclerView which displays all lessons.
 *
 * Activities that contain this fragment must implement the
 * [OnBaseLessonListFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
abstract class BaseLessonListFragment : MultiSelectFragment<Lesson, BaseLessonListAdapter>() {

    protected var listener: OnBaseLessonListFragmentInteractionListener? = null
    protected lateinit var lessonListViewModel: LessonListViewModel
    protected abstract val type: BaseLessonListType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //instantiate the viewModel
        lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)
        //add observer to viewModel to set lessons into the adapter
        lessonListViewModel.lessons.observe(this, Observer {
            adapter.setLessons(it)
        })
        adapter = BaseLessonListAdapter(context!!, this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lesson_list, container, false)
        //instantiate the recyclerView with its adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.lesson_recyclerview)
        recyclerView.adapter = adapter
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lesson_list_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        searchView.setOnCloseListener {
            adapter.resetLessons()
            false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPause() {
        //hide keyboard from searchView
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBaseLessonListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnBaseLessonListFragmentInteractionListener")
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
    interface OnBaseLessonListFragmentInteractionListener {
        fun onBaseLessonListItemInteraction(lessonId: Long, type: BaseLessonListType)
        fun onToNewLessonInteraction()
    }

    enum class BaseLessonListType {
        LESSON_LIST, ADD_LIST
    }
}
package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.shared.ConfirmationDialogFragment
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectFragment
import com.kaiserpudding.howtheywrite.shared.setSafeOnClickListener

/**
 * A simple [Fragment] subclass.
 *
 * It contains a recyclerView which displays all lessons.
 *
 * Activities that contain this fragment must implement the
 * [LessonListFragment.OnLessonListFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class LessonListFragment
    : MultiSelectFragment<Lesson>(),
        ConfirmationDialogFragment.ConfirmationDialogListener {

    private var listener: OnLessonListFragmentInteractionListener? = null
    private lateinit var lessonListViewModel: LessonListViewModel

    override val actionMenuId: Int
        get() = R.menu.selection_delete_menu

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showConfirmationDialog()
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //instantiate the viewModel
        lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lesson_list, container, false)

        //instantiate the recyclerView with its adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.lesson_recyclerview)
        adapter = LessonListAdapter(view.context, this)
        recyclerView.adapter = adapter

        //add observer to viewModel to set lessons into the adapter
        lessonListViewModel.lessons.observe(this, Observer {
            (adapter as LessonListAdapter).setLessons(it)
        })

        val fab = view.findViewById<FloatingActionButton>(R.id.new_lessen_fab)
        fab.setSafeOnClickListener {
            onNewLessonButtonPressed()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lesson_list_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (adapter as LessonListAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (adapter as LessonListAdapter).filter.filter(newText)
                return false
            }

        })
        searchView.setOnCloseListener {
            (adapter as LessonListAdapter).resetLessons()
            false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPause() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        super.onPause()
    }



    private fun onNewLessonButtonPressed() {
        listener?.onToNewLessonInteraction()
    }

    override fun onListInteraction(itemId: Long) {
        listener?.onLessonListItemInteraction(itemId)
    }

    private fun showConfirmationDialog() {
        val dialog = ConfirmationDialogFragment(this)
        dialog.show(childFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick() {
        if (adapter.selectedIdArray.contains(0)) {
            val toast = Toast.makeText(context, "The All lesson can't be deleted", Toast.LENGTH_LONG)
            toast.show()
        }
        lessonListViewModel.deleteLessons(adapter.selectedIdArray)
        actionMode?.finish()
    }

    override fun onDialogNegativeClick() {
        val toast = Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLessonListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnLessonListFragmentInteractionListener")
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
    interface OnLessonListFragmentInteractionListener {
        fun onLessonListItemInteraction(lessonId: Long)
        fun onToNewLessonInteraction()
    }
}

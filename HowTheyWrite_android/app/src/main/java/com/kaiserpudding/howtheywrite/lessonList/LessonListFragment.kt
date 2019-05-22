package com.kaiserpudding.howtheywrite.lessonList

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kaiserpudding.howtheywrite.R

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
    : Fragment(),
    LessonListAdapter.OnLessonListAdapterItemInteractionListener{

    private var listener: OnLessonListFragmentInteractionListener? = null
    private lateinit var lessonListViewModel: LessonListViewModel

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
        val adapter = LessonListAdapter(view.context, this)
        recyclerView.adapter = adapter

        //add observer to viewModel to set lessons into the adapter
        lessonListViewModel.lessons.observe(this, Observer { it?.let { it1 -> adapter.setLessons(it1) } })

        val fab = view.findViewById<FloatingActionButton>(R.id.new_lessen_fab)
        fab.setOnClickListener{
            onNewLessonButtonPressed()
        }

        return view
    }

    private fun onListItemPressed(lessonId: Int, lessonName: String) {
        listener?.onLessonListItemInteraction(lessonId, lessonName)
    }

    private fun onNewLessonButtonPressed() {
        listener?.onToNewLessonInteraction()
    }

    override fun onLessonListAdapterItemInteraction(lessonId: Int, lessonName: String) {
        onListItemPressed(lessonId, lessonName)
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
        fun onLessonListItemInteraction(lessonId: Int, lessonName: String)
        fun onToNewLessonInteraction()
    }
}

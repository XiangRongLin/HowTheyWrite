package com.kaiserpudding.howtheywrite.lessonList

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
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
 * Activities that contain this fragment must implement the
 * [LessonListFragment.OnLessonListFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LessonListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LessonListFragment
    : Fragment(),
    LessonListAdapter.OnLessonListAdapterItemInteractionListener{

    private var listenerList: OnLessonListFragmentInteractionListener? = null
    private lateinit var lessonListViewModel: LessonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lesson_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.lesson_recyclerview)
        val adapter = LessonListAdapter(view.context, this)
        recyclerView.adapter = adapter

        lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)
        lessonListViewModel.lessons.observe(this, Observer { it?.let { it1 -> adapter.setLessons(it1) } })

        val fab = view.findViewById<FloatingActionButton>(R.id.add_lessen_fab)
        fab.setOnClickListener{
            val intent = Intent(activity, NewLessonActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    fun onButtonPressed(lessonId: Int) {
        listenerList?.onLessonListFragmentInteraction(lessonId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLessonListFragmentInteractionListener) {
            listenerList = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLessonListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerList = null
    }

    override fun onLessonListAdapterItemInteraction(lessonId: Int) {
        onButtonPressed(lessonId)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnLessonListFragmentInteractionListener {
        fun onLessonListFragmentInteraction(lessonId: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LessonListFragment.
         */
        @JvmStatic
        fun newInstance() = LessonListFragment()
    }
}

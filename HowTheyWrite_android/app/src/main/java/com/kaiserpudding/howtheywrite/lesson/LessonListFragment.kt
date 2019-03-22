package com.kaiserpudding.howtheywrite.lesson

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.lesson.LessonListFragment.OnLessonListFragmentInteractionListener
import com.kaiserpudding.howtheywrite.model.Lesson


/**
 * A fragment representing a list of lessons.
 *
 * Activities containing this fragment MUST implement
 * the [OnLessonListFragmentInteractionListener] interface.
 */
class LessonListFragment : Fragment() {

    companion object {

        const val NEW_LESSON_ACTIVITY_REQUEST_CODE = 1
    }

    private var listener: OnLessonListFragmentInteractionListener? = null
    private lateinit var adapter: LessonListAdapter
    private lateinit var lessonViewModel: LessonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = LessonListAdapter(context!!, listener)

        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)
        lessonViewModel.lessons.observe(this, Observer<List<Lesson>> { adapter.setLessons(it!!) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_list, container, false)

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.lesson_recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        //Set OnClickListener for new Lesson Button
        val fab = view.findViewById<FloatingActionButton>(R.id.add_lesson_fab)
        fab.setOnClickListener {
            val intent = Intent(context, NewLessonActivity::class.java)
            startActivityForResult(intent, NEW_LESSON_ACTIVITY_REQUEST_CODE)
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnLessonListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnLessonListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_LESSON_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            val lesson = Lesson(data.getStringExtra(NewLessonActivity.EXTRA_REPLY))
            lessonViewModel.insertLesson(lesson)
        }
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other fragments
     * contained in that activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnLessonListFragmentInteractionListener {

        // TODO: Update argument type and name
        fun onLessonListFragmentInteraction(lesson: Lesson)
    }

}

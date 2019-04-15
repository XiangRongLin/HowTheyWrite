package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders

import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Lesson

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewLessonFragment.OnNewLessonFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class NewLessonFragment : Fragment() {
    private var listener: OnNewLessonFragmentInteractionListener? = null

    private lateinit var newLessonEditText: EditText
    private lateinit var lessonListViewModel: LessonListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_lesson, container, false)

        newLessonEditText = view.findViewById(R.id.edit_word)

        lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)

        val button = view.findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            //TODO editText.text is mutable, could have changed after or while checkEditText
            if (checkEditText()) {
                lessonListViewModel.insertLesson(Lesson(newLessonEditText.text.toString()))
            }
        }

        return view
    }

    /**
     * Checks if the input is not empty and not no other lesson with the same name already exists
     * and displays an error message accordingly if not
     * TODO remove display error message out of this method
     */
    private fun checkEditText(): Boolean {
        return when {
            TextUtils.isEmpty(newLessonEditText.text) -> {
                newLessonEditText.error = resources.getString(R.string.error_empty_field)
                false
            }
            //TODO remove !!
            lessonListViewModel.lessonNames!!.contains(newLessonEditText.text.toString()) -> {
                newLessonEditText.error = resources.getString(R.string.error_duplicate_lesson_name)
                false
            }
            else -> true
        }
    }

    fun onFinish() {
        listener?.onNewLessonFinishInteraction()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewLessonFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNewCharacterFragmentInteractionListener")
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
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnNewLessonFragmentInteractionListener {
        fun onNewLessonFinishInteraction()
    }

}
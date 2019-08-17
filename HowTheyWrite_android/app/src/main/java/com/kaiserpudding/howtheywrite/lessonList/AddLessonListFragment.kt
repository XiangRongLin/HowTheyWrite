package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R
import java.lang.RuntimeException

class AddLessonListFragment : BaseLessonListFragment() {

    private var lessonId = 0L
    private lateinit var lessonName: String
    override val type: BaseLessonListType = BaseLessonListType.ADD_LIST
    override val actionMenuId = R.menu.selection_add_menu
    private var listenerSecond: OnAddLessonListFragmentInteractionListener? = null

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_confirm -> {
                lessonListViewModel.addCharactersOfLessonToLesson(adapter.selectedIdArray, lessonId)
                mode.finish()
                listenerSecond?.onFinish()
                true
            }
            else -> false
        }
    }

    override fun onListInteraction(itemId: Long) {
        listenerSecond?.onAddLessonListItemInteraction(lessonId, lessonName, itemId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs : AddLessonListFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
        lessonName = safeArgs.lessonName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)
        val fab = view.findViewById<FloatingActionButton>(R.id.new_lessen_fab)
        fab.visibility = View.INVISIBLE
        listenerSecond?.updateTitle("Add to $lessonName")
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddLessonListFragmentInteractionListener) {
            listenerSecond = context
        } else {
            throw RuntimeException("$context must implement OnAddLessonListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerSecond = null
    }

    interface OnAddLessonListFragmentInteractionListener {
        fun onAddLessonListItemInteraction(addToId: Long, addToName: String, selectedId: Long)
        fun updateTitle(title: String)
        fun onFinish()
    }
}
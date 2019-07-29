package com.kaiserpudding.howtheywrite.lessonList

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R

class AddLessonListFragment : BaseLessonListFragment() {

    private var lessonId = 0L
    private lateinit var lessonName: String
    override val type: BaseLessonListType = BaseLessonListType.ADD_LIST
    override val actionMenuId = R.menu.empty_menu

    override fun onMyActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return false
    }

    override fun onListInteraction(itemId: Long) {
        listener?.onBaseLessonListItemInteraction(itemId, type)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs : AddLessonListFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)
        val fab = view.findViewById<FloatingActionButton>(R.id.new_lessen_fab)
        fab.visibility = View.INVISIBLE
        return view
    }
}
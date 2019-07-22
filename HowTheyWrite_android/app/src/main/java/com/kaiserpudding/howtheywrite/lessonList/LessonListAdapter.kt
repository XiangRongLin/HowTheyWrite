package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectAdapter

/**
 * Adapter for the recyclerView for lessions
 *
 * Classes using this must implement [MultiSelectAdapter.MultiSelectAdapterItemInteractionListener]
 * to handle interaction events with an item in the list.
 *
 * @property context
 * @property listener
 */
class LessonListAdapter(
        private val context: Context,
        listener: MultiSelectAdapterItemInteractionListener)
    : MultiSelectAdapter<Lesson>(listener) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override val viewHolderId = R.id.lessonTextView
    private val all = Lesson("All")

    init {
        list = mutableListOf(all)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_lesson, parent, false)
        return createViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val current = list!![position]
        //TODO proper string representation of lesson
        holder.textView.text = current.toString()
    }

    internal fun setLessons(lessons: List<Lesson>) {
        val combined = mutableListOf(all)
        combined.addAll(lessons)
        list = combined
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size
        else 0
    }

    override fun getMyItemId(position: Int): Long {
        return if (list != null) list!![position].id
        else 0
    }
}

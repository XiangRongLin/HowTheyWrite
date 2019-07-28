package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
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
    : MultiSelectAdapter<Lesson>(listener), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override val viewHolderId = R.id.lessonTextView
    private val all = Lesson("All")
    private var filteredList: List<Lesson>?

    init {
        list = listOf(all)
        filteredList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_lesson, parent, false)
        return createViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val current = filteredList!![position]
        //TODO proper string representation of lesson
        holder.textView.text = current.toString()
    }

    internal fun setLessons(lessons: List<Lesson>) {
        val combined = mutableListOf(all)
        combined.addAll(lessons)
        list = combined
        filteredList = list
        notifyDataSetChanged()
    }

    internal fun resetLessons() {
        filteredList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (filteredList != null) filteredList!!.size
        else 0
    }

    override fun getMyItemId(position: Int): Long {
        return if (filteredList != null) filteredList!![position].id
        else 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                if (constraint.isEmpty()) {
                    results.values = list
                } else {
                    val resultList = mutableListOf<Lesson>()
                    for (lesson in list!!) {
                        if (lesson.name.contains(constraint, true)) {
                            resultList.add(lesson)
                        }
                    }
                    results.values = resultList
                }
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as List<Lesson>?
                notifyDataSetChanged()
            }
        }
    }
}

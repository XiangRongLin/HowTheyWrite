package com.kaiserpudding.howtheywrite.lessonList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Lesson

/**
 * Adapter for the recyclerView for lessions
 *
 * Classes using this must implement [LessonListAdapter.OnLessonListAdapterItemInteractionListener]
 * to handle interaction events with an item in the list.
 *
 * @property context
 * @property listener
 */
class LessonListAdapter(
        private val context: Context,
        private val listener: OnLessonListAdapterItemInteractionListener)
    : RecyclerView.Adapter<LessonListAdapter.LessonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var lessons: List<Lesson>? = null

    inner class LessonViewHolder constructor(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        val lessonItemView: TextView

        init {
            itemView.setOnClickListener { onAdapterItemPressed() }
            lessonItemView = itemView.findViewById(R.id.lessonTextView)
        }

        private fun onAdapterItemPressed() {
            //+ 1 because adapter positions starts at 0 while Room ids start at 1
            listener.onLessonListAdapterItemInteraction(adapterPosition + 1, lessonItemView.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_lesson, parent, false)
        return LessonViewHolder(context, itemView)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        if (lessons != null) {
            val current = lessons!![position]
            //TODO proper string representation of lesson
            holder.lessonItemView.text = current.toString()
        } else {
            //TODO NOT displayed
            holder.lessonItemView.setText(R.string.no_lesson)
        }
    }

    internal fun setLessons(lessons: List<Lesson>) {
        this.lessons = lessons
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (lessons != null)
            lessons!!.size
        else
            0
    }

    interface OnLessonListAdapterItemInteractionListener {
        fun onLessonListAdapterItemInteraction(lessonId: Int, lessonName: String)
    }

}

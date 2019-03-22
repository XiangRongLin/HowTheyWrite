package com.kaiserpudding.howtheywrite.lesson

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.lesson.LessonListFragment.OnLessonListFragmentInteractionListener
import com.kaiserpudding.howtheywrite.model.Lesson

class LessonListAdapter constructor(private val context: Context, private val listener: OnLessonListFragmentInteractionListener?)
    : RecyclerView.Adapter<LessonListAdapter.LessonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var lessons: List<Lesson>? = null

    inner class LessonViewHolder (context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        val lessonItemView: TextView = itemView.findViewById(R.id.lessonTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val itemView = inflater.inflate(R.layout.lesson_recyclerview_item, parent, false)
        return LessonViewHolder(context, itemView)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val current = lessons!![position]
        //TODO proper string representation of lesson
        holder.lessonItemView.text = current.toString()
        holder.lessonItemView.setOnClickListener {
            listener?.onLessonListFragmentInteraction(lessons!![position])
        }
    }

    internal fun setLessons(lessons: List<Lesson>) {
        this.lessons = lessons
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (lessons != null) lessons!!.size
        else 0
    }
}

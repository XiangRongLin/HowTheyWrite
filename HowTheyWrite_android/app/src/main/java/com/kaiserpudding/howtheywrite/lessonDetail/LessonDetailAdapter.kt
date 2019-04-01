package com.kaiserpudding.howtheywrite.lessonDetail

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailActivity
import com.kaiserpudding.howtheywrite.model.Character


//TODO https://stackoverflow.com/questions/49969278/set-a-click-listener-to-a-recyclerview
class LessonDetailAdapter(private val context: Context) : RecyclerView.Adapter<LessonDetailAdapter.CharacterViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var characters: List<Character>? = null

    inner class CharacterViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        val characterItemView: TextView

        init {
            itemView.setOnClickListener { v ->
                val replyIntent = Intent(v.context, CharacterDetailActivity::class.java)
                replyIntent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, characters!![adapterPosition].id)
                context.startActivity(replyIntent)

            }
            characterItemView = itemView.findViewById(R.id.characterTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = inflater.inflate(R.layout.character_recyclerview_item, parent, false)
        return CharacterViewHolder(context, itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        var representation: String?
        if (characters != null) {
            val current = characters!![position]
            representation = current.hanzi
            if (representation == null) {
                representation = current.pinyin
            }
        } else {
            //TODO NOT displayed
            representation = "No Characters"
        }
        holder.characterItemView.text = representation
    }

    internal fun setCharacters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (characters != null)
            characters!!.size
        else
            0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

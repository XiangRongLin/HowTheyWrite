package com.kaiserpudding.howtheywrite.lesson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Lesson;
import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonViewHolder> {

  class LessonViewHolder extends RecyclerView.ViewHolder {

    private final TextView lessonItemView;

    private LessonViewHolder (Context context, View itemView) {
      super(itemView);
      itemView.setOnClickListener((v) -> {
        Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
          }
      );
      lessonItemView = itemView.findViewById(R.id.textView);
    }
  }

  private final LayoutInflater inflater;
  private final Context context;

  private List<Lesson> lessons;

  LessonListAdapter(Context context) {
    this.context = context;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.lesson_recyclerview_item, parent, false);
    return new LessonViewHolder(context, itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
    if (lessons != null) {
      Lesson current = lessons.get(position);
      //TODO proper string representation of lesson
      holder.lessonItemView.setText(current.toString());
    } else {
      holder.lessonItemView.setText(R.string.no_lesson);
    }
  }

  void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if (lessons != null)
      return  lessons.size();
      else return 0;
  }

}

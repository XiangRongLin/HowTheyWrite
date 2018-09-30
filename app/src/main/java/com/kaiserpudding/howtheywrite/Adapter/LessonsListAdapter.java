package com.kaiserpudding.howtheywrite.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Lesson;
import java.util.List;

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.LessonViewHolder> {

  class LessonViewHolder extends RecyclerView.ViewHolder {
    private final TextView lessonItemView;

    private LessonViewHolder(View itemView) {
      super(itemView);
      lessonItemView = itemView.findViewById(R.id.textView);
    }
  }

  private final LayoutInflater inflater;
  private List<Lesson> lessons;

  public LessonsListAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = inflater.inflate(R.layout.recyclerview_item, viewGroup, false);
    return new LessonViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull LessonViewHolder lessonViewHolder, int i) {
    if (lessons != null) {
      Lesson current = lessons.get(i);
      lessonViewHolder.lessonItemView.setText(current.getName());
    } else {
      lessonViewHolder.lessonItemView.setText("no Lessons");
    }
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  @Override
  public int getItemCount() {
    if (lessons != null)
      return lessons.size();
    else return 0;
  }
}

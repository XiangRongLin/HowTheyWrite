package com.kaiserpudding.howtheywrite.lessonList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.lessonDetail.LessonDetailActivity;
import com.kaiserpudding.howtheywrite.model.Lesson;
import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonViewHolder> {

  public static final String SELECTED_LESSON_ID = "howTheyWrite.selectedLessonId";

  class LessonViewHolder extends RecyclerView.ViewHolder {

    private final TextView lessonItemView;

    private LessonViewHolder(Context context, View itemView) {
      super(itemView);
      itemView.setOnClickListener(v -> {
        Intent intent = new Intent(context, LessonDetailActivity.class);
        intent.putExtra(LessonDetailActivity.REPLY_LESSON_ID, lessons.get(getAdapterPosition()).getId() + 1);
        context.startActivity(intent);
      });
      lessonItemView = itemView.findViewById(R.id.lessonTextView);
    }
  }

  private final LayoutInflater inflater;
  private final Context context;

  private List<Lesson> lessons;

  LessonListAdapter(Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
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
      //TODO NOT displayed
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

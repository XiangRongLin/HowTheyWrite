package com.kaiserpudding.howtheywrite.characterList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailActivity;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;

class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>{



  class CharacterViewHolder extends RecyclerView.ViewHolder {

    private final TextView characterItemView;

    private CharacterViewHolder(Context context, View itemView) {
      super(itemView);
      itemView.setOnClickListener(v -> {
        Intent replyIntent = new Intent(v.getContext(), CharacterDetailActivity.class);
        replyIntent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, getAdapterPosition() + 1);
        context.startActivity(replyIntent);

      });
      characterItemView = itemView.findViewById(R.id.characterTextView);
    }
  }

  private final LayoutInflater inflater;
  private final Context context;

  private List<Character> characters;

  CharacterListAdapter(Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.character_recyclerview_item, parent, false);
    return new CharacterViewHolder(context, itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position){
    String represetation;
    if (characters != null) {
      Character current = characters.get(position);
      represetation = current.getHanzi();
      if (represetation == null) {
        represetation = current.getPinyin();
      }
    } else {
      //TODO NOT displayed
      represetation = "No Characters";
    }
    holder.characterItemView.setText(represetation);
  }

  void setCharacters(List<Character> characters) {
    this.characters = characters;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if (characters != null)
      return characters.size();
      else return 0;
  }

}

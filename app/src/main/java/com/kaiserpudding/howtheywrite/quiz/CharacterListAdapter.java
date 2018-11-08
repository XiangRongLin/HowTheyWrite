//package com.kaiserpudding.howtheywrite.quiz;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.kaiserpudding.howtheywrite.R;
//import com.kaiserpudding.howtheywrite.model.Character;
//import java.util.List;
//
//public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {
//
//  class CharacterViewHolder extends RecyclerView.ViewHolder {
//    private final TextView characterViewItem;
//
//    private CharacterViewHolder(Context context, View itemView) {
//      super(itemView);
//      itemView.setOnClickListener((v) -> {
//        //TODO proper click Listener
//        Toast.makeText(context, getAdapterPosition(), Toast.LENGTH_SHORT);
//      });
//      characterViewItem = itemView.findViewById(R.id.characterTextView);
//    }
//  }
//
//  private final LayoutInflater inflater;
//  private final Context context;
//
//  private List<Character> characters;
//
//  CharacterListAdapter(Context context) {
//    this.context = context;
//    inflater = LayoutInflater.from(context);
//  }
//
//  @NonNull
//  @Override
//  public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//    View itemView = inflater.inflate(R.layout.character_recyclerview_item, parent, false);
//    return new CharacterViewHolder(context, itemView);
//  }
//
//  @Override
//  public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
//    if (characters != null) {
//      Character current = characters.get(position);
//      //TODO proper string representation of characters
//      holder.characterViewItem.setText(current.getKanji());
//    } else {
//      //TODO proper
//      holder.characterViewItem.setText("No Character");
//    }
//
//  }
//
//  void setCharacters(List<Character> characters) {
//    this.characters = characters;
//    notifyDataSetChanged();
//  }
//
//  @Override
//  public int getItemCount() {
//    if (characters != null)
//      return characters.size();
//    else return 0;
//  }
//
//}

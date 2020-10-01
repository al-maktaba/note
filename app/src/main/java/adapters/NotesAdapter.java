package adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import activities.listeners.NotesListener;
import entities.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder>{

        private List<Note> notes;
        private NotesListener notesListener;
        private Timer timer;
        private List<Note> notesSource;

    public NotesAdapter(List<Note> notes, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
        notesSource = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        holder.setNote(notes.get(position));
        holder.noteItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesListener.onNoteClicked(notes.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView titleText, noteText, textDataTime;
        CardView noteItemCard;
        RoundedImageView imageNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            noteText = itemView.findViewById(R.id.noteText);
            textDataTime = itemView.findViewById(R.id.textDataTime);
            noteItemCard = itemView.findViewById(R.id.noteItemCard);
            imageNote = itemView.findViewById(R.id.imageNote);
        }

        private void setNote(Note note){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(note.getTitle().equals("")){
                titleText.setVisibility(View.GONE);
                params.setMargins(40,40,40,40);
                noteText.setLayoutParams(params);
            } else {
                titleText.setVisibility(View.VISIBLE);
                titleText.setText(note.getTitle());
                params.setMargins(40,5,40,40);
                noteText.setLayoutParams(params);
            }
            noteText.setText(note.getTextNote());
            textDataTime.setText(note.getDataTime());

            if(note.getImagePath() != null){
                imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            } else {
                imageNote.setVisibility(View.GONE);
            }

            if(note.getColor() != null){
                noteItemCard.setCardBackgroundColor(Color.parseColor(note.getColor()));
                if(note.getColor().equals("#000000")){
                    titleText.setTextColor(Color.parseColor("#FFFFFF"));
                     noteText.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    titleText.setTextColor(Color.parseColor("#000000"));
                    noteText.setTextColor(Color.parseColor("#000000"));
                }
            } else {
                noteItemCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    public void searchNote(final String searchKeyword){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyword.trim().isEmpty()) {
                    notes = notesSource;
                } else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for(Note note : notesSource){
                        if(note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase()) ||
                                note.getTextNote().toLowerCase().contains(searchKeyword.toLowerCase())){
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },  500);
    }

    public void cancelTimer(){
        if(timer != null){
            timer.cancel();
        }
    }
}

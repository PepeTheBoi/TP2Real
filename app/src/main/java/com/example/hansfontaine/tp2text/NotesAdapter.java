package com.example.hansfontaine.tp2text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hans Fontaine on 2017-12-10.
 */

public class NotesAdapter extends ArrayAdapter<Note>{


    /**
     * on fait hériter compètement la note de son parent, ArrayAdapter d'objet
     * @param context héritage
     * @param resource héritage
     * @param objects héritage
     */
    public NotesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    /**
     * layout de l'apparence d'une case de note dans le listview
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_item, null);
        }

        Note note = getItem(position);

        if (note != null){
            TextView titre = (TextView) convertView.findViewById(R.id.list_notes_titre);
            TextView date = (TextView) convertView.findViewById(R.id.list_notes_date);
            TextView contenu = (TextView) convertView.findViewById(R.id.list_notes_contenu);

            titre.setText(note.getTitre());
            date.setText(note.getDateTimeEnString(getContext()));
            if(note.getContenu().length()> 30){
                contenu.setText(note.getContenu().substring(0,30));
            }else{
                contenu.setText(note.getContenu());
            }
        }

        return convertView;
    }
}

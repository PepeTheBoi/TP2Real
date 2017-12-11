package com.example.hansfontaine.tp2text;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * Apparence initiale de l'application
     */
    private ListView mainListViewNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainListViewNotes =(ListView) findViewById(R.id.mainlistView);
    }

    /**
     * Layout du menu dans le toolbar
     * @param menu ajout d'un menu à option
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Items dans le toolbar
     * @param item envoi du signal de l'item dont le onitemclicklistener a été activé
     * @return true
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_nouvelle_note:
                Intent intent = new Intent(this, NoteActivity.class);
                startActivity(intent);
                break;
            case R.id.action_a_propos:
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.a_propos, (ViewGroup)findViewById(R.id.customToast));
                Toast toast = new Toast(getApplicationContext());

                toast.setView(layout);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.action_fun_facts:
                new android.app.AlertDialog.Builder(getApplicationContext())
                        .setTitle("@string/faits_amusants !")
                        .setMessage("En fait le prank c'est que ça crash")
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override

    /**
     * Ajout des notes dans le listView du mainActivity
     * Ajout du onitemclicklistener pour ouvrir la note en question
     */
    protected void onResume() {
        super.onResume();
        mainListViewNotes.setAdapter(null);

        ArrayList<Note> notes = Actions.appellerToutesLesNotesSauvegardees(this);

        //Lorsqu'il n'y a pas de note enregistrée, il y a un toast
        if(notes == null || notes.size() ==0){
            Toast.makeText(this, "pas de notes", Toast.LENGTH_SHORT).show();
            return;
        } else {//Si il y a des éléments dans le directory, on les ajoute à la liste
            NotesAdapter notesAdapter = new NotesAdapter(this, R.layout.note_item, notes);
            mainListViewNotes.setAdapter(notesAdapter);

            mainListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String filename = ((Note)mainListViewNotes.getItemAtPosition(position)).getDateTime() + Actions.FILE_EXTENSION;

                    Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                    intent.putExtra("NOTE_FILE", filename);
                    startActivity(intent);
                }
            });
        }

    }
}

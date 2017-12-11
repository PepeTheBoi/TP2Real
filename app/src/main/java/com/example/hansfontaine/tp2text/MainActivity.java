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

    private ListView mainListViewNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainListViewNotes =(ListView) findViewById(R.id.mainlistView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
                        .setMessage("Saviez-vous que cette section a ete cree dans le seul but " +
                                "de faire plaisir a Alexandre.")
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainListViewNotes.setAdapter(null);

        ArrayList<Note> notes = Actions.appellerToutesLesNotesSauvegardees(this);

        if(notes == null || notes.size() ==0){
            Toast.makeText(this, "pas de notes", Toast.LENGTH_SHORT).show();
            return;
        }else {
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

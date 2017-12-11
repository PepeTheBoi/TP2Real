package com.example.hansfontaine.tp2text;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText noteEditTextTitre;
    private EditText noteEditTextContenu;

    private String nomFileNotes;
    private Note NoteGeneree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteEditTextTitre = (EditText) findViewById(R.id.noteedittextTitre);
        noteEditTextContenu = (EditText) findViewById(R.id.noteEditTextContenu);

        nomFileNotes = getIntent().getStringExtra("NOTE_FILE");

        if(nomFileNotes != null && !nomFileNotes.isEmpty()){
            NoteGeneree = Actions.getNoteParNom(getApplicationContext(), nomFileNotes);

            if(NoteGeneree!=null){
                noteEditTextTitre.setText(NoteGeneree.getTitre());
                noteEditTextContenu.setText(NoteGeneree.getContenu());
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_sauvegarder:
                sauvegarderNote();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
        }
        return true;
    }

    private void deleteNote() {
        if(NoteGeneree == null){
            finish();
        }else{
            Actions.deleteNote(getApplicationContext(), NoteGeneree.getDateTime() + Actions.FILE_EXTENSION);
            Toast.makeText(getApplicationContext(), "Note supprimée, vous pouvez retourner au menu principal", Toast.LENGTH_LONG);
        }
    }

    private void sauvegarderNote(){
        Note note;
        if(NoteGeneree == null){
            note = new Note(System.currentTimeMillis(), noteEditTextTitre.getText().toString(), noteEditTextContenu.getText().toString());
        }else{
            note = new Note(NoteGeneree.getDateTime(), noteEditTextTitre.getText().toString(), noteEditTextContenu.getText().toString());
        }
        if(Actions.sauvegarderNote(this, note)){
            Toast.makeText(this, "note sauvegardée", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "sauvegarde échouée", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

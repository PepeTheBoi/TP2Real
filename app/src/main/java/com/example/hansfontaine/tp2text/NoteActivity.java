package com.example.hansfontaine.tp2text;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText noteEditTextTitre;
    private EditText noteEditTextContenu;

    private String nomFileNotes;
    private Note NoteGeneree;

    @Override
    /**
     * Apparence initiale de l'application
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteEditTextTitre = (EditText) findViewById(R.id.noteedittextTitre);
        noteEditTextContenu = (EditText) findViewById(R.id.noteEditTextContenu);

        nomFileNotes = getIntent().getStringExtra("NOTE_FILE");

        ImageButton supprimer = (ImageButton)findViewById(R.id.supprimer);
        ImageButton reglage = (ImageButton)findViewById(R.id.reglage);
        ImageButton save = (ImageButton)findViewById(R.id.save);


        //Listener pour supprimer la note
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteNote();

            }
        });

        //Listener pour acceder aux reglages
        reglage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, SettingActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Listener pour sauvgarder
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sauvegarderNote();

            }
        });

        if(nomFileNotes != null && !nomFileNotes.isEmpty()){
            NoteGeneree = Actions.getNoteParNom(getApplicationContext(), nomFileNotes);

            if(NoteGeneree!=null){
                noteEditTextTitre.setText(NoteGeneree.getTitre());
                noteEditTextContenu.setText(NoteGeneree.getContenu());
            }
        }
    }

    /**
     * layout du toolbar dans la note
     * @param menu on envoie le menu main de note
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    /**
     * selection des items dans un menu
     * @param item item cliquable dans le menu
     * @return retourne true
     */
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

    /**
     * Suppression d'une note via la fonction Action.deleteNote
     */
    private void deleteNote() {
        if(NoteGeneree == null){
            finish();
        }else{
            Actions.deleteNote(getApplicationContext(), NoteGeneree.getDateTime() + Actions.FILE_EXTENSION);
            finish();
            Toast.makeText(getApplicationContext(), "Note supprimée!", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Sauvegarde d'un note via la fonction Action.deleteNote
     */
    private void sauvegarderNote(){
        Note note;
        if(NoteGeneree == null){
            note = new Note(System.currentTimeMillis(), noteEditTextTitre.getText().toString(), noteEditTextContenu.getText().toString());
        }else{
            note = new Note(NoteGeneree.getDateTime(), noteEditTextTitre.getText().toString(), noteEditTextContenu.getText().toString());
        }
        if(Actions.sauvegarderNote(this, note)){
            Toast.makeText(this, R.string.sauve, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.save_fail, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    /**
     * Reception de options de la note
     * @param requestCode code voulu par l'activité note
     * @param resultCode code renvoyer par les settings
     * @param data intent reçu de l'activité settings
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                EditText contenu = (EditText) findViewById(R.id.noteEditTextContenu);
                String color = data.getStringExtra("COLOR");
                String size = data.getStringExtra("SIZE");
                String font = data.getStringExtra("FONT");
                if (!color.equals("")){
                    if (color.equals("black")){
                        contenu.setTextColor(Color.parseColor("#000000"));
                    }else if (color.equals("blue")){
                        contenu.setTextColor(Color.parseColor("#3F48CC"));
                    }else if (color.equals("red")){
                        contenu.setTextColor(Color.parseColor("#EC1C24"));
                    }else if (color.equals("green")){
                        contenu.setTextColor(Color.parseColor("#0ED145"));
                    }else if (color.equals("yellow")){
                        contenu.setTextColor(Color.parseColor("#FFF200"));
                    }
                }
                if(!size.equals("")){
                    contenu.setTextSize(Float.parseFloat(size));
                }
                if(!font.equals("")){
                    if (color.equals("Serif")){
                        contenu.setTypeface(Typeface.createFromAsset(getAssets(),"font/sans-serif.ttf"));
                    }else if (color.equals("Casual")){
                        contenu.setTypeface(Typeface.createFromAsset(getAssets(),"font/casual.ttf"));
                    }else if (color.equals("Aclonica")){
                        contenu.setTypeface(Typeface.createFromAsset(getAssets(),"font/aclonica.ttf"));
                    }else if (color.equals("Caesar Dressing")){
                        contenu.setTypeface(Typeface.createFromAsset(getAssets(),"font/caesar_dressing.ttf"));
                    }else if (color.equals("Homemade Apple")){
                        contenu.setTypeface(Typeface.createFromAsset(getAssets(),"font/homemade_apple.ttf"));
                    }

                }

            }
        }
    }
}

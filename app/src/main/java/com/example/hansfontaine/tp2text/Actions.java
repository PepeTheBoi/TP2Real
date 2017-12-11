package com.example.hansfontaine.tp2text;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Hans Fontaine on 2017-12-10.
 */

public class Actions {

    /**
     * La façon dont les notes enregistrées von apparaitre dans le directory (leur fin)
     */
    public static final String FILE_EXTENSION = ".bin";

    /**
     * Fonction accessible à toutes les classes pour sauvegarder dans un même directory
     * @param context contexte de la note
     * @param note note dans laquelle on écrit
     * @return retourne si la sauvegarde est réussie
     */
    public static boolean sauvegarderNote(Context context, Note note) {
        //La seule variable qui sera propre à une seule note est la date de création, donc on la sauvegarde avec ca
        String filename = String.valueOf(note.getDateTime()+ FILE_EXTENSION);

        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try {

            fileOutputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(note);
            objectOutputStream.close();
            fileOutputStream.close();

        }catch(IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Fonction accessible à toutes les classes pour générer des notes dans un même directory
     * @param context contexte du mainActivity
     * @return retourne un arraylist de note
     */
    public static ArrayList<Note> appellerToutesLesNotesSauvegardees(Context context){
        //On ressort un arraylist de notes pour les afficher
        ArrayList<Note> notes = new ArrayList<>();

        File filesDirectory = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        //Accès à toutes les notes enregistrées
        for(String file: filesDirectory.list()){
            if(file.endsWith(FILE_EXTENSION)){
                noteFiles.add(file);
            }
        }

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        //Ajout des notes dans le directory dans l'arraylist, qui sera retourné
        for(int i=0; i<noteFiles.size(); i++){
            try{
                fileInputStream = context.openFileInput(noteFiles.get(i));
                objectInputStream = new ObjectInputStream(fileInputStream);

                notes.add((Note)objectInputStream.readObject());
                fileInputStream.close();
                objectInputStream.close();
            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return notes;
    }

    /**
     * Accès à une note par son nom pour l'ouvrir
     * @param context contexte du mainActivity
     * @param filename nom du fichier
     * @return retourne soit une note, soit rien
     */
    public static Note getNoteParNom(Context context, String filename){
        File file = new File(context.getFilesDir(), filename);
        Note note;

        if(file.exists()){
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;

            try{
                fileInputStream = context.openFileInput(filename);
                objectInputStream = new ObjectInputStream(fileInputStream);

                note = (Note) objectInputStream.readObject();

                fileInputStream.close();
                objectInputStream.close();
            }catch(IOException| ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
            return note;
        }
        return null;
    }

    /**
     * Supprime une note dans le directory en utilisant un filename
     * @param applicationContext contexte de l'application
     * @param filename nom du fichier
     */
    public static void deleteNote(Context applicationContext, String filename) {
        File fileDirectory = applicationContext.getFilesDir();
        File file = new File(fileDirectory, filename);
        if(file.exists()){
            file.delete();
        }
    }
}

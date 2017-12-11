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

    public static final String FILE_EXTENSION = ".bin";

    public static boolean sauvegarderNote(Context context, Note note) {
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

    public static ArrayList<Note> appellerToutesLesNotesSauvegardees(Context context){
        ArrayList<Note> notes = new ArrayList<>();

        File filesDirectory = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for(String file: filesDirectory.list()){
            if(file.endsWith(FILE_EXTENSION)){
                noteFiles.add(file);
            }
        }

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

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

    public static void deleteNote(Context applicationContext, String filename) {
        File fileDirectory = applicationContext.getFilesDir();
        File file = new File(fileDirectory, filename);
        if(file.exists()){
            file.delete();
        }
    }
}

package com.example.hansfontaine.tp2text;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hans Fontaine on 2017-12-10.
 */

public class Note implements Serializable{
    private  long DateTime;
    private String Titre;
    private String Contenu;

    public Note(long dateTime, String titre, String contenu) {
        DateTime = dateTime;
        Titre = titre;
        Contenu = contenu;
    }

    public void setDateTime(long dateTime) {
        DateTime = dateTime;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public long getDateTime() {
        return DateTime;
    }

    public String getTitre() {
        return Titre;
    }

    public String getContenu() {
        return Contenu;
    }

    public String getDateTimeEnString(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(this.getDateTime()));
    }
}

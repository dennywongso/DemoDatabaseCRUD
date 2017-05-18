package com.example.a15017096.demodatabasecrud;

import java.io.Serializable;

/**
 * Created by 15017096 on 18/5/2017.
 */

public class Note implements Serializable {
    private int id;
    private String noteContent;

    public Note (int id, String noteContent){
        this.id = id;
        this.noteContent = noteContent;
    }

    public int getID(){
        return id;
    }
    public String getNoteContent(){
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
    @Override
    public String toString() {
        return "ID:" + id + ", " + noteContent;
    }


}

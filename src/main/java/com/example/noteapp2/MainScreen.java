package com.example.noteapp2;

import java.util.ArrayList;

public class MainScreen {
    private ArrayList<String> noteList;

    MainScreen(){
        this.noteList = new ArrayList<>();
    }

    public ArrayList<String> getNoteList() {
        return noteList;
    }
    public boolean addToList(String str){
        for (String st: noteList) {
            if (st.equals(str)){
                return false;
            }
        }
        noteList.add(str);
        return true;
    }
    public void removeFromList(String str){
        if (noteList.contains(str)){
            noteList.remove(str);
        }
        else{
            System.out.println(str + ", is not on the note content..");
        }
    }
}

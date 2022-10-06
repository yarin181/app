package com.example.noteapp2;

import java.util.ArrayList;

public class BasicNote {
    private String titleOfTheNote;
    private ArrayList<String> listOfContents;

    BasicNote(String titleOfTheNote){
        this.titleOfTheNote = titleOfTheNote;
        this.listOfContents = new ArrayList<String>();
    }
    public boolean isExist(String str){
        for (String st: listOfContents) {
            if (st.equals(str)){
                return true;
            }
        }
        return false;
    }
    public void addToList(String str){
        listOfContents.add(str);
    }
    public void removeFromList(String str){
        if (listOfContents.contains(str)){
            listOfContents.remove(str);
        }
        else{
            System.out.println(str + ", is not on the note content..");
        }
    }

    public ArrayList<String> getListOfContents() {
        return listOfContents;
    }
}

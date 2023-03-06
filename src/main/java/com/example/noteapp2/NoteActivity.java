package com.example.noteapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class NoteActivity extends AppCompatActivity {
    private String FILE_NAME;
    EditText content;
    ListView listView;
    BasicNote noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Gson gson = new Gson();
        FILE_NAME = "noteInfoOf"+getIntent().getStringExtra("name")+".json";
        noteContent = new BasicNote(getIntent().getStringExtra("name"));
        setTitle(getIntent().getStringExtra("name"));

        loadFromFile();
        content = findViewById(R.id.insertBox);
        saveToFile();
        load();
    }

    private void saveToFile(){
        Gson gson = new Gson();
        try {
            // Convert JsonObject to String Format
            String userString = gson.toJson(noteContent);
            // Define the File Path and its Name
            File file = new File(this.getFilesDir(), FILE_NAME);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadFromFile(){
        File file;
        try {
            file = new File(this.getFilesDir(), FILE_NAME);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            // This response will have Json Format String
            String response = stringBuilder.toString();
            Gson gson1 = new Gson();
            noteContent = gson1.fromJson(response, BasicNote.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(View v){
        String text = content.getText().toString();

        if(text.length() > 0 ){
            noteContent.addToList(text);
        }
        else {
            (Toast.makeText(this,"Not a valid content" ,Toast.LENGTH_LONG)).show();
        }
        content.getText().clear();
        load();
        saveToFile();

    }
    public void load() {
        listView = findViewById(R.id.note_content2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(NoteActivity.this,
                android.R.layout.simple_list_item_1,noteContent.getListOfContents());
        listView.setAdapter(arrayAdapter);
    }
}

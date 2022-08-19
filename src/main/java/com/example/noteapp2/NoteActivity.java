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
    private String FILE_NAME; //=  ;
    //private final String FILE_NAME = getIntent().getStringExtra("name");
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

//        try {
//            // Convert JsonObject to String Format
//            String userString = gson.toJson(noteContent);
//            // Define the File Path and its Name
//            File file = new File(this.getFilesDir(), FILE_NAME);
//            FileWriter fileWriter = new FileWriter(file);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(userString);
//            bufferedWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            openFileInput(FILE_NAME);
//        } catch (FileNotFoundException e) {
//            File file = new File(FILE_NAME);
//        }
//        setTitle(getIntent().getStringExtra("name"));
//        load();
//
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
//        if(!Files.exists(Paths.get(this.getFilesDir().getPath(),FILE_NAME))){
//            file = new File(this.getFilesDir(), FILE_NAME);
//        }
//        else {
//            file = File.createTempFile(this.getFilesDir().toString(),FILE_NAME);
//        }

            //File file = new File(this.getFilesDir(), FILE_NAME);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

//        File contentFile = new File(FILE_NAME);
//        try {
//            if (contentFile.createNewFile()){
//                System.out.println("file created ");
//            }; // if file already exists will do nothing
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (PrintWriter os = new PrintWriter( // wrapper with many ways of writing strings
//                new OutputStreamWriter( // wrapper that can write strings
//                        new FileOutputStream(FILE_NAME,false)))) {
//            // wrapper with many ways of writing strings
//            // wrapper that can write strings
//
//            os.println(text);
//
//        } catch (IOException e) {
//            System.out.println(" Something went wrong while writing !");
//        }
        // Exception might have happened at constructor
        // closes fileOutputStream too
        //        FileOutputStream fos = null;
//
//        PrintWriter out = null;
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//
//        try {
//            fw = new FileWriter("/data/data/com.example.noteapp2/files/text bla.txt", true);
//            bw = new BufferedWriter(fw);
//            out = new PrintWriter(bw);
//            out.println(text);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bw = new BufferedWriter(fw);
//        out = new PrintWriter(bw);

//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
//            writer.append("\n");
//            writer.append(text);
//
////            FileOutputStream fis = new FileOutputStream(FILE_NAME);
////            FileWriter fw = new FileWriter(String.valueOf(fis));
////            fw.write(text);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        FileOutputStream fos = null;
//        try {
//            //FileWriter fw = new FileWriter(FILE_NAME,true);
//            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
//            ArrayList<String> oldContent = loadContent();
//            for (String str:oldContent) {
//                fos.write(str.getBytes());
//                fos.write("\n".getBytes());
//            }
//            fos.write(text.getBytes());
//
//
//            content.getText().clear();
//            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
//                    Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        content.getText().clear();
        load();
        saveToFile();

    }
    public void load() {
        listView = findViewById(R.id.note_content2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NoteActivity.this,
                android.R.layout.simple_list_item_1,noteContent.getListOfContents());
        listView.setAdapter(arrayAdapter);

//        FileInputStream fis = null;
//
//        try {
//            fis = openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            //StringBuilder sb = new StringBuilder();
//            String text;
//
//            ArrayList<String> arrayList = new ArrayList<>();
//            while ((text = br.readLine()) != null) {
//                arrayList.add(text);
//            }
//            listView = (ListView) findViewById(R.id.note_content2);
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NoteActivity.this,
//                    android.R.layout.simple_list_item_1,arrayList);
//
//            listView.setAdapter(arrayAdapter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


    private ArrayList<String> loadContent(){
        FileInputStream fis = null;
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("bka");
        arrayList.add("bka2");
        arrayList.add("bka3");
        arrayList.add("bka4");

//
//        File file = new File(FILE_NAME);
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        InputStreamReader isr = new InputStreamReader(fis);
//        BufferedReader br = new BufferedReader(isr);
//
//        String line = null;
//        while(true){
//            try {
//                if ((line = br.readLine()) == null) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //process the line
//            arrayList.add(line);
//        }
//        try {
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            FileReader myObj = new FileReader(new FileOutputStream(FILE_NAME));
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                arrayList.add(data);
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }


//        try {
//            fis = openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            String text;
//
//            arrayList = new ArrayList<>();
//            while ((text = br.readLine()) != null) {
//                arrayList.add(text);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return arrayList;
    }
}

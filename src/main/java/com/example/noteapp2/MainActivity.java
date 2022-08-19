package com.example.noteapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    MainScreen mainScreen;
    Button addNoteButton;
    private final String FILE_NAME = "MainScreenContent.json";
    private static final String TAG = "MainActivity";
    ActivityResultLauncher<Intent> activityLauncher;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFromFile();
        //load();
        if (mainScreen ==null){
            mainScreen = new MainScreen();
        }

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d(TAG,"onActivityResult: ");
                if (result.getResultCode() == RESULT_OK){
                    Intent intent =result.getData();
                    if (intent != null){
                        String noteName = intent.getStringExtra("noteName");
                        System.out.println(noteName);
                        mainScreen.addToList(noteName);
                        saveToFile();

                        Intent intent2 = new Intent(getApplicationContext(), NoteActivity.class);
                        intent2.putExtra("name", noteName);

                        startActivity(intent2);
                    }
                }
            }
        });
        listView = (ListView) findViewById(R.id.notesList);

//        ArrayList<String> arrayList = new ArrayList<>();
//
//        arrayList.add("str");
//
//        arrayList.add("Shop list");
//        arrayList.add("Things to buy");
//        arrayList.add("Shifts");
//        arrayList.add("Tracking numbers ");
//        arrayList.add("General list");
//        arrayList.add("Not general list");
//        arrayList.add("Not A list....");
        this.arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,mainScreen.getNoteList());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("name", mainScreen.getNoteList().get(i));

                startActivity(intent);

            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                deleteNote(mainScreen.getNoteList().get(pos));
                Log.v("long clicked","pos: " + pos);

                return true;
            }
        });

        listView.setAdapter(arrayAdapter);
    }
    private void deleteNote(String str){

        mainScreen.removeFromList(str);
        this.arrayAdapter.remove(str);
        String FILE_NAME = "noteInfoOf"+str+".json";
        File file = new File(this.getFilesDir(),FILE_NAME);
        (Toast.makeText(MainActivity.this, str + " was deleted" ,Toast.LENGTH_LONG)).show();
        file.delete();
        saveToFile();
//        loadFromFile();
//        load();
    }


//    private ArrayAdapter<String> arrayAdapterGenerate(){
//         class MyListAdaper extends ArrayAdapter<String> {
//            private int layout;
//            private List<String> mObjects;
//            private MyListAdaper(Context context, int resource, List<String> objects) {
//                super(context, resource, objects);
//                mObjects = objects;
//                layout = resource;
//            }
//
//            @Override
//            public View getView(final int position, View convertView, ViewGroup parent) {
//                RecyclerView.ViewHolder mainViewholder = null;
//                if(convertView == null) {
//                    LayoutInflater inflater = LayoutInflater.from(getContext());
//                    convertView = inflater.inflate(layout, parent, false);
//                    RecyclerView.ViewHolder viewHolder = new ViewHolder();
//                    viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
//                    viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
//                    viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
//                    convertView.setTag(viewHolder);
//                }
//                mainViewholder = (RecyclerView.ViewHolder) convertView.getTag();
//                mainViewholder.button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                mainViewholder.title.setText(getItem(position));
//
//                return convertView;
//            }
//        }
//        public class ViewHolder {
//
//            ImageView thumbnail;
//            TextView title;
//            Button button;
//        }
//    }

    public void openNoteAddScreen(View view){
        Intent intent = new Intent(MainActivity.this, AddNotePopUP.class);
        activityLauncher.launch(intent);
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
            this.mainScreen = gson1.fromJson(response, MainScreen.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveToFile(){
        Gson gson = new Gson();
        try {
            // Convert JsonObject to String Format
            String userString = gson.toJson(this.mainScreen);
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

    public void load() {
        if (this.mainScreen == null){
            return;
        }
        listView = findViewById(R.id.note_content2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,this.mainScreen.getNoteList());
        listView.setAdapter(arrayAdapter);
    }
}
package com.example.noteapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The type Add note pop up.
 *
 */
public class AddNotePopUP extends AppCompatActivity {

    private EditText editText;
    private Intent resultIntent;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        editText = findViewById(R.id.textBox2);
        resultIntent = new Intent();
        getWindow().getAttributes().x = 0;
        getWindow().getAttributes().y = -200;
        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.22));
    }


    /**
     * Add note.
     *
     * @param view the view
     */
    public void addNote(View view) {
        if (editText.getText().length() > 0) {
            resultIntent.putExtra("noteName", editText.getText().toString());
            setResult(AddNotePopUP.RESULT_OK, resultIntent);
            this.finish();
        } else {
            (Toast.makeText(this, "Not a valid note title", Toast.LENGTH_LONG)).show();
        }
    }
}
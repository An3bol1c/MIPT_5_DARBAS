package com.example.mipt5darbas;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private static final String TAG = "AddNoteActivity";
    private static final int MAX_NAME_LENGTH = 50;

    private EditText etNoteName;
    private EditText etNoteContent;
    private NoteStorage noteStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_add_note);

        noteStorage = new NoteStorage(this);
        etNoteName = findViewById(R.id.etNoteName);
        etNoteContent = findViewById(R.id.etNoteContent);
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> saveNote());
    }



    private void saveNote() {
        Log.d(TAG, "saveNote called");
        String name = etNoteName.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();

        if (!isInputValid(name, content)) {
            return;
        }

        noteStorage.addNote(new Note(name, content));
        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isInputValid(String name, String content) {
        Log.d(TAG, "isInputValid called");
        if (name.isEmpty()) {
            etNoteName.setError(getString(R.string.error_name_empty));
            return false;
        }
        if (name.length() > MAX_NAME_LENGTH) {
            etNoteName.setError(getString(R.string.error_name_too_long));
            return false;
        }
        if (noteStorage.noteExists(name)) {
            etNoteName.setError(getString(R.string.error_name_exists));
            return false;
        }
        if (content.isEmpty()) {
            etNoteContent.setError(getString(R.string.error_content_empty));
            return false;
        }
        return true;
    }
}
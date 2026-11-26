package com.example.mipt5darbas;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DeleteNoteActivity extends AppCompatActivity {
    private static final String TAG = "DeleteNoteActivity";

    private Spinner spNotes;
    private NoteStorage noteStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_delete_note);

        noteStorage = new NoteStorage(this);
        spNotes = findViewById(R.id.spNotes);
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> deleteSelectedNote());

        fillSpinner();
    }

    private void fillSpinner() {
        Log.d(TAG, "fillSpinner called");
        List<String> names = new ArrayList<>();
        for (Note note : noteStorage.loadNotes()) {
            names.add(note.name);
        }
        if (names.isEmpty()) {
            Toast.makeText(this, R.string.no_notes, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        spNotes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names));
    }

    private void deleteSelectedNote() {
        Log.d(TAG, "deleteSelectedNote called");
        int position = spNotes.getSelectedItemPosition();
        if (position == Spinner.INVALID_POSITION) {
            return;
        }
        noteStorage.deleteNote(position);
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }
}
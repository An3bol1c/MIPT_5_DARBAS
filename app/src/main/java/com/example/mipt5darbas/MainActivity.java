package com.example.mipt5darbas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView lvNotes;
    private NoteStorage noteStorage;
    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);

        noteStorage = new NoteStorage(this);
        lvNotes = findViewById(R.id.lvNotes);
        lvNotes.setOnItemClickListener((parent, view, position, id) -> showNoteContent(position));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        refreshNotesList();
    }

    private void refreshNotesList() {
        Log.d(TAG, "refreshNotesList called");
        notes = noteStorage.loadNotes();
        List<String> names = new ArrayList<>();
        for (Note note : notes) {
            names.add(note.name);
        }
        lvNotes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
    }

    private void showNoteContent(int position) {
        Log.d(TAG, "showNoteContent called, position = " + position);
        Note note = notes.get(position);
        new AlertDialog.Builder(this)
                .setTitle(note.name)
                .setMessage(note.content)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected called");
        int id = item.getItemId();
        if (id == R.id.menuAddNote) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.menuDeleteNote) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
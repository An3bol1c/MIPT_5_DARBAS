package com.example.mipt5darbas;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    private static final String TAG = "NoteStorage";
    private static final String PREFS_NAME = "notes_prefs";
    private static final String KEY_NOTES = "notes_json";
    private static final String JSON_NAME = "name";
    private static final String JSON_CONTENT = "content";

    private final SharedPreferences preferences;

    public NoteStorage(Context context) {
        Log.d(TAG, "NoteStorage constructor called");
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public List<Note> loadNotes() {
        Log.d(TAG, "loadNotes called");
        List<Note> notes = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(preferences.getString(KEY_NOTES, "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                notes.add(new Note(obj.getString(JSON_NAME), obj.getString(JSON_CONTENT)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "loadNotes failed", e);
        }
        return notes;
    }

    private void saveNotes(List<Note> notes) {
        Log.d(TAG, "saveNotes called, count = " + notes.size());
        JSONArray array = new JSONArray();
        try {
            for (Note note : notes) {
                JSONObject obj = new JSONObject();
                obj.put(JSON_NAME, note.name);
                obj.put(JSON_CONTENT, note.content);
                array.put(obj);
            }
        } catch (JSONException e) {
            Log.e(TAG, "saveNotes failed", e);
            return;
        }
        preferences.edit().putString(KEY_NOTES, array.toString()).apply();
    }

    public boolean noteExists(String name) {
        Log.d(TAG, "noteExists called: " + name);
        for (Note note : loadNotes()) {
            if (note.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void addNote(Note note) {
        Log.d(TAG, "addNote called: " + note.name);
        List<Note> notes = loadNotes();
        notes.add(note);
        saveNotes(notes);
    }

    public void deleteNote(int index) {
        Log.d(TAG, "deleteNote called, index = " + index);
        List<Note> notes = loadNotes();
        if (index >= 0 && index < notes.size()) {
            notes.remove(index);
            saveNotes(notes);
        }
    }
}
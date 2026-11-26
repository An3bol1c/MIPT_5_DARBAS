package com.example.mipt5darbas;

import android.util.Log;

public class Note {
    private static final String TAG = "Note";

    public final String name;
    public final String content;

    public Note(String name, String content) {
        Log.d(TAG, "Note created: " + name);
        this.name = name;
        this.content = content;
    }
}
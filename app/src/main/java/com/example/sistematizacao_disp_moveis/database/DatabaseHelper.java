package com.example.sistematizacao_disp_moveis.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movieapp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIES = "movies";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DIRECTOR = "director";
    private static final String COLUMN_YEAR = "year";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DIRECTOR + " TEXT,"
                + COLUMN_YEAR + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // CRUD Operations
    public void addMovie(Movie movie) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movie.getTitle());
            values.put(COLUMN_DIRECTOR, movie.getDirector());
            values.put(COLUMN_YEAR, movie.getYear());
            db.insert(TABLE_MOVIES, null, values);
        }
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECTOR)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR))
                    );
                    movieList.add(movie);
                } while (cursor.moveToNext());
            }
        }
        return movieList;
    }

    // Update movie
    public void updateMovie(Movie movie) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movie.getTitle());
            values.put(COLUMN_DIRECTOR, movie.getDirector());
            values.put(COLUMN_YEAR, movie.getYear());
            db.update(TABLE_MOVIES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(movie.getId())});
        }
    }

    // Delete movie
    public void deleteMovie(int movieId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_MOVIES, COLUMN_ID + " = ?", new String[]{String.valueOf(movieId)});
        }
    }

    // Search movies by title
    public ArrayList<Movie> searchMovies(String title) {
        ArrayList<Movie> movieList = new ArrayList<>();
        String searchQuery = "SELECT * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_TITLE + " LIKE ?";

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + title + "%"})) {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECTOR)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR))
                    );
                    movieList.add(movie);
                } while (cursor.moveToNext());
            }
        }
        return movieList;
    }
}


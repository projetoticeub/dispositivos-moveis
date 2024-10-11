package com.example.sistematizacao_disp_moveis.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistematizacao_disp_moveis.R;
import com.example.sistematizacao_disp_moveis.database.DatabaseHelper;
import com.example.sistematizacao_disp_moveis.database.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView directorTextView;
    private TextView yearTextView;
    private Button editButton;
    private Button deleteButton;
    private DatabaseHelper db;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = findViewById(R.id.title_text_view);
        directorTextView = findViewById(R.id.director_text_view);
        yearTextView = findViewById(R.id.year_text_view);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        db = new DatabaseHelper(this);

        // Recuperando o ID do filme
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movie_id", -1);

        displayMovieDetails(movieId);

        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(MovieDetailActivity.this, AddEditMovieActivity.class);
            editIntent.putExtra("movie_id", movieId);
            startActivityForResult(editIntent, 1);
        });

        deleteButton.setOnClickListener(v -> {
            db.deleteMovie(movieId);
            finish();
        });
    }

    private void displayMovieDetails(int movieId) {
        Movie movie = db.getAllMovies().stream().filter(m -> m.getId() == movieId).findFirst().orElse(null);
        if (movie != null) {
            titleTextView.setText(movie.getTitle());
            directorTextView.setText(movie.getDirector());
            yearTextView.setText(String.valueOf(movie.getYear()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            displayMovieDetails(movieId);
        }
    }
}
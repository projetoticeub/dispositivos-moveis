package com.example.sistematizacao_disp_moveis.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistematizacao_disp_moveis.R;
import com.example.sistematizacao_disp_moveis.database.DatabaseHelper;
import com.example.sistematizacao_disp_moveis.database.Movie;

public class AddEditMovieActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText directorEditText;
    private EditText yearEditText;
    private Button saveButton;
    private DatabaseHelper db;
    private int movieId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        titleEditText = findViewById(R.id.title_edit_text);
        directorEditText = findViewById(R.id.director_edit_text);
        yearEditText = findViewById(R.id.year_edit_text);
        saveButton = findViewById(R.id.save_button);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent.hasExtra("movie_id")) {
            movieId = intent.getIntExtra("movie_id", -1);
            if (movieId != -1) {
                loadMovieDetails(movieId);
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String director = directorEditText.getText().toString();
                int year = Integer.parseInt(yearEditText.getText().toString());

                if (movieId == -1) {

                    Movie movie = new Movie(0, title, director, year);
                    db.addMovie(movie);
                } else {

                    Movie movie = new Movie(movieId, title, director, year);
                    db.updateMovie(movie);
                }

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void loadMovieDetails(int movieId) {
        Movie movie = db.getAllMovies().stream()
                .filter(m -> m.getId() == movieId)
                .findFirst()
                .orElse(null);

        if (movie != null) {
            titleEditText.setText(movie.getTitle());
            directorEditText.setText(movie.getDirector());
            yearEditText.setText(String.valueOf(movie.getYear()));
        }
    }
}

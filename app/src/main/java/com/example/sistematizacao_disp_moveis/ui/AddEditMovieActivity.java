package com.example.sistematizacao_disp_moveis.ui;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        titleEditText = findViewById(R.id.title_edit_text);
        directorEditText = findViewById(R.id.director_edit_text);
        yearEditText = findViewById(R.id.year_edit_text);
        saveButton = findViewById(R.id.save_button);
        db = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String director = directorEditText.getText().toString();
                int year = Integer.parseInt(yearEditText.getText().toString());

                Movie movie = new Movie(0, title, director, year);
                db.addMovie(movie);
                finish();
            }
        });
    }
}

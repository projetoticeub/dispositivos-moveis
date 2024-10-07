package com.example.sistematizacao_disp_moveis.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private DatabaseHelper db; // Declare o DatabaseHelper
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

        // Inicializa o DatabaseHelper
        db = new DatabaseHelper(this);

        // Recuperar o ID do filme passado pela Intent
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movie_id", -1);

        // Exibir detalhes do filme
        displayMovieDetails(movieId);

        // Configurar ações dos botões
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(MovieDetailActivity.this, AddEditMovieActivity.class);
                editIntent.putExtra("movie_id", movieId);
                startActivity(editIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteMovie(movieId);
                finish(); // Voltar à tela principal
            }
        });
    }

    private void displayMovieDetails(int movieId) {
        // Verifique se a lista de filmes não é nula
        if (db != null) {
            Movie movie = db.getAllMovies().stream().filter(m -> m.getId() == movieId).findFirst().orElse(null);
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                directorTextView.setText(movie.getDirector());
                yearTextView.setText(String.valueOf(movie.getYear()));
            }
        } else {
            // Tratar erro: db é nulo
            titleTextView.setText("Erro ao carregar filme");
        }
    }



}

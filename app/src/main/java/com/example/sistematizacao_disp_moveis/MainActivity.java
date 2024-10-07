package com.example.sistematizacao_disp_moveis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistematizacao_disp_moveis.database.DatabaseHelper;
import com.example.sistematizacao_disp_moveis.database.Movie;
import com.example.sistematizacao_disp_moveis.ui.AddEditMovieActivity;
import com.example.sistematizacao_disp_moveis.ui.MovieDetailActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button addButton;
    private DatabaseHelper db;
    private ArrayList<Movie> movieList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa os componentes
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_view);
        addButton = findViewById(R.id.add_button);

        // Configura o botão para adicionar um filme
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
                startActivityForResult(intent, 1); // Usa startActivityForResult para obter o resultado
            }
        });

        // Carrega os filmes ao iniciar a atividade
        loadMovies();

        // Configura o listener de cliques para a lista de filmes
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movieList.get(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie_id", selectedMovie.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies(); // Atualiza a lista ao voltar para a atividade
    }

    private void loadMovies() {
        movieList = db.getAllMovies(); // Obtém a lista de filmes do banco de dados
        ArrayList<String> titles = new ArrayList<>();
        for (Movie movie : movieList) {
            titles.add(movie.getTitle()); // Adiciona apenas os títulos à lista
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter); // Define o adapter na ListView
    }
}

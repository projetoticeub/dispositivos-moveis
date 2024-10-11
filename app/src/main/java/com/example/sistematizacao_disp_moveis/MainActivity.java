package com.example.sistematizacao_disp_moveis;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_view);
        addButton = findViewById(R.id.add_button);
        searchEditText = findViewById(R.id.search_edit_text);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
            startActivityForResult(intent, 1);
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loadMovies();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie selectedMovie = movieList.get(position);
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("movie_id", selectedMovie.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies();
    }

    private void loadMovies() {
        movieList = db.getAllMovies();
        updateListView(movieList);
    }

    private void searchMovies(String query) {
        movieList = db.searchMovies(query);
        updateListView(movieList);
    }

    private void updateListView(ArrayList<Movie> movies) {
        ArrayList<String> titles = new ArrayList<>();
        for (Movie movie : movies) {
            titles.add(movie.getTitle());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
    }
}



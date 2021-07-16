package com.projects.mybooklist.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.projects.mybooklist.R;
import com.projects.mybooklist.adapters.BooksAdapter;
import com.projects.mybooklist.entities.Book;
import com.projects.mybooklist.entities.BooksData;
import com.projects.mybooklist.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BooksAdapter booksAdapter;
    ArrayList<Book> books;
    TextInputEditText main_EDT_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        initViews();

        initRecycleView();

    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.books_REC_card);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        books = new ArrayList<>();

        books = readBooksFromJson();

        booksAdapter = new BooksAdapter(this, books);
        recyclerView.setAdapter(booksAdapter);
        booksAdapter.setItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = books.get(position).getTitle();
                Toast.makeText(getApplicationContext(), "The book " + title + " is chosen at index " + position, Toast.LENGTH_SHORT).show();
            }
        });

        initSearchFilter();

    }

    private void initViews() {
        main_EDT_search = findViewById(R.id.main_EDT_search);

    }

    private void initSearchFilter() {
        main_EDT_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(text.toLowerCase())
                    || book.getBody().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }
        booksAdapter.filteredList(filteredList,text);
    }

    public ArrayList<Book> readBooksFromJson() {

        String json = Objects.requireNonNull(Utils.loadJSONFromAsset(this, "books (1).json"));

        Gson gson = new Gson();

        BooksData booksData = gson.fromJson(json, BooksData.class);

        for (Book book : booksData.getData()) {
            Log.i("booksLog", "readBooksFromJson: " + book);
        }

        return new ArrayList<>(Arrays.asList(booksData.getData()));
    }


}
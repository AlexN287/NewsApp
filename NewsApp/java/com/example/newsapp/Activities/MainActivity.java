package com.example.newsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsapp.CustomAdapter;
import com.example.newsapp.Listeners.OnFetchDataListener;
import com.example.newsapp.Listeners.SelectListener;
import com.example.newsapp.Model.Article;
import com.example.newsapp.Model.NewsApiResponse;
import com.example.newsapp.R;
import com.example.newsapp.RequestManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Button btnBusiness, btnEntertainment, btnGeneral, btnHealth, btnScience, btnSports, btnTechnology;
    SearchView searchbar;
    ImageView myAccount;
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Article> articles, String message) {
            if(articles.isEmpty())
            {
                Toast.makeText(MainActivity.this, "No articles found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                showNews(articles);
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Article> articles) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        customAdapter = new CustomAdapter(this, articles, this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchbar = findViewById(R.id.search_bar);

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager requestManager = new RequestManager(MainActivity.this);
                requestManager.getArticles(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        myAccount = findViewById(R.id.img_account);

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        btnBusiness = findViewById(R.id.btn_business);
        btnEntertainment = findViewById(R.id.btn_entertainment);
        btnGeneral = findViewById(R.id.btn_general);
        btnHealth = findViewById(R.id.btn_health);
        btnScience = findViewById(R.id.btn_science);
        btnSports = findViewById(R.id.btn_sports);
        btnTechnology = findViewById(R.id.btn_Technology);
        btnBusiness.setOnClickListener(this);
        btnEntertainment.setOnClickListener(this);
        btnGeneral.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
        btnScience.setOnClickListener(this);
        btnSports.setOnClickListener(this);
        btnTechnology.setOnClickListener(this);

        RequestManager requestManager = new RequestManager(this);
        requestManager.getArticles(listener, "general", null);
    }

    @Override
    public void onClickedArticle(Article article) {
        startActivity(new Intent(MainActivity.this, SelectedArticleActivity.class)
                .putExtra("data", article));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        RequestManager requestManager = new RequestManager(this);
        requestManager.getArticles(listener, category, null);
    }
}
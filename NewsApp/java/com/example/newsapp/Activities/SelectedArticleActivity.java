package com.example.newsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Model.Article;
import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

public class SelectedArticleActivity extends AppCompatActivity {

    Article article;
    TextView textTitle, textAuthor, textTime, textDetail, textContent;
    ImageView articleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_article);
        textTitle = findViewById(R.id.text_detail_title);
        textAuthor = findViewById(R.id.text_detail_author);
        textTime = findViewById(R.id.text_detail_time);
        textDetail = findViewById(R.id.text_detail_detail);
        textContent = findViewById(R.id.text_detail_content);
        articleImage = findViewById(R.id.img_detail_news);

        article = (Article) getIntent().getSerializableExtra("data");

        textTitle.setText(article.getTitle());
        textAuthor.setText(article.getAuthor());
        textTime.setText(article.getPublishedAt());
        textDetail.setText(article.getDescription());
        textContent.setText(article.getContent());
        Picasso.get().load(article.getUrlToImage()).into(articleImage);
    }
}
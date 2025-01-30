package com.example.newsapp.Listeners;

import com.example.newsapp.Model.Article;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<Article> articles,String message);
    void onError(String message);
}

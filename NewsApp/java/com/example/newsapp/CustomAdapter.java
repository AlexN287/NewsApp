package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Listeners.SelectListener;
import com.example.newsapp.Model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Article> articles;
    private SelectListener selectListener;

    public CustomAdapter(Context context, List<Article> articles, SelectListener selectListener) {
        this.context = context;
        this.articles = articles;
        this.selectListener = selectListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.main_items_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textTitle.setText(articles.get(position).getTitle());
        holder.textSource.setText(articles.get(position).getSource().getName());

        if(articles.get(position).getUrlToImage()!=null)
        {
            Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.imgArticle);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.onClickedArticle(articles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}

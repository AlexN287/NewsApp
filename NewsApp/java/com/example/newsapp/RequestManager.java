package com.example.newsapp;

import android.content.Context;
import android.widget.Toast;

import com.example.newsapp.Listeners.OnFetchDataListener;
import com.example.newsapp.Model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    private Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getArticles(OnFetchDataListener listener, String category, String query)
    {
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> request =callNewsApi.callArticles("us", category, query, "");

        try{
            request.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(context,"Error fetching the data", Toast.LENGTH_SHORT);
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request failed");
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public interface CallNewsApi{
        @GET("top-headlines")
        Call<NewsApiResponse> callArticles(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String apiKey
        );
    }
}

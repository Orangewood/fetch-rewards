package com.example.fetch;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetch.model.Hiring;
import com.example.fetch.server.Api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private String BASE_URL = "https://fetch-hiring.s3.amazonaws.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apiService = retrofit.create(Api.class);

        apiService.getItems().enqueue(new Callback<List<Hiring>>() {
            @Override
            public void onResponse(Call<List<Hiring>> call, Response<List<Hiring>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hiring> hiringData = response.body();
                    hiringData.removeIf(data -> data.getName() == null || data.getName().trim().isEmpty());


                    Collections.sort(hiringData, new Comparator<Hiring>() {
                        @Override
                        public int compare(Hiring o1, Hiring o2) {
                            if (o1.getListId() != o2.getListId()) {
                                return Integer.compare(o1.getListId(), o2.getListId());
                            }
                            return o1.getName().compareTo(o2.getName());
                        }
                    });


                    listAdapter = new ListAdapter(hiringData);
                    recyclerView.setAdapter(listAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Hiring>> call, Throwable t) {
                Log.e("MainActivity", "Network request failed", t);
            }
        });
    }
}

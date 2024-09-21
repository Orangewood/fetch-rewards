package com.example.fetch.server;
import com.example.fetch.model.Hiring;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
     @GET("hiring.json")
     Call<List<Hiring>> getItems();
}

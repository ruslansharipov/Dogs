package com.sharipov.dogs.ResponseStructures;

import android.database.Observable;

import com.sharipov.dogs.ResponseStructures.Breeds;
import com.sharipov.dogs.ResponseStructures.RandomImage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://dog.ceo/api/";

    @GET("breeds/list/all")
    Call<Breeds> getBreeds();

    @GET("breeds/image/random")
    Call<RandomImage> getRandomImage();

    @GET("breed/{subBreed}/{breed}images/random")
    Call<RandomImage> getRandomImage(@Path("subBreed") String subBreed, @Path("breed") String breed);

}
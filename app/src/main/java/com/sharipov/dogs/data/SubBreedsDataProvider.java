package com.sharipov.dogs.data;

import android.util.Log;

import com.sharipov.dogs.response_structures.Api;
import com.sharipov.dogs.response_structures.RandomImage;
import com.sharipov.dogs.response_structures.SubBreeds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubBreedsDataProvider {

    private static final String TAG = "qqq";
    private String breed;
    private Api api;
    private List<SubBreedObject> subBreedsList;

    public interface OnGetImageListener {
        void onSuccess(String imageUri);

        void onFail(Throwable t);
    }

    public interface OnGetMessageListener {
        void onSuccess(List<String> message);

        void onFail(Throwable t);
    }

    public interface OnGetListListener {
        void onSuccess(List<SubBreedObject> subBreedObjectList);

        void onFail(Throwable t);
    }

    public SubBreedsDataProvider(String breed) {
        this.breed = breed;
        api = ApiInstance.getApi();
        subBreedsList = new LinkedList<>();
    }

    private void getMessage(OnGetMessageListener onGetMessageListener) {
        Call<SubBreeds> call = api.getSubBreedsList(breed);
        call.enqueue(new Callback<SubBreeds>() {
            @Override
            public void onResponse(Call<SubBreeds> call, Response<SubBreeds> response) {
                onGetMessageListener.onSuccess(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<SubBreeds> call, Throwable t) {
                onGetMessageListener.onFail(t);
            }
        });
    }

    public void getSubBreedsList(OnGetListListener onGetListListener) {
        getMessage(new OnGetMessageListener() {
            @Override
            public void onSuccess(List<String> message) {
                if (message.size() == 1) {
                    message.add(breed);
                }
                for (String s : message) {
                    if (breed.equals(s)) {
                        getImageUri(breed, new OnGetImageListener() {
                            @Override
                            public void onSuccess(String imageUri) {
                                subBreedsList.add(new SubBreedObject(getTitle(s), s, imageUri));
                                Log.d(TAG, "onSuccess: " + subBreedsList.size() + ")" + breed + " " + imageUri);
                                if (subBreedsList.size() == message.size()) {
                                    onGetListListener.onSuccess(subBreedsList);
                                }
                            }

                            @Override
                            public void onFail(Throwable t) {

                            }
                        });
                    } else {
                        getImageUri(breed, s, new OnGetImageListener() {
                            @Override
                            public void onSuccess(String imageUri) {
                                subBreedsList.add(new SubBreedObject(getTitle(s), s, imageUri));
                                Log.d(TAG, "onSuccess: " + subBreedsList.size() + ")" + breed + " " + imageUri);
                                if (subBreedsList.size() == message.size()) {
                                    onGetListListener.onSuccess(subBreedsList);
                                }
                            }

                            @Override
                            public void onFail(Throwable t) {
                                Log.d(TAG, "onFail: " + t.toString());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFail(Throwable t) {
                onGetListListener.onFail(t);
            }
        });
    }

    private void getImageUri(String breed, String subBreed, SubBreedsDataProvider.OnGetImageListener onGetImageListener) {
        Call<RandomImage> callBreeds = api.getSubBreedImage(breed, subBreed);
        callBreeds.enqueue(new Callback<RandomImage>() {
            @Override
            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
                RandomImage randomImage = response.body();
                onGetImageListener.onSuccess(randomImage.getMessage());
            }

            @Override
            public void onFailure(Call<RandomImage> call, Throwable t) {
                onGetImageListener.onFail(t);
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    private void getImageUri(String breed, SubBreedsDataProvider.OnGetImageListener onGetImageListener) {
        Call<RandomImage> callBreeds = api.getBreedImage(breed);
        callBreeds.enqueue(new Callback<RandomImage>() {
            @Override
            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
                RandomImage randomImage = response.body();
                onGetImageListener.onSuccess(randomImage.getMessage());
            }

            @Override
            public void onFailure(Call<RandomImage> call, Throwable t) {
                onGetImageListener.onFail(t);
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    private String getTitle(String s) {
        char[] sCharArray = s.toCharArray();
        sCharArray[0] = Character.toUpperCase(sCharArray[0]);
        return new String(sCharArray);
    }

    public static List<SubBreedObject> getFilteredList(String query, List<SubBreedObject> list) {
        List<SubBreedObject> newList = new ArrayList<>();
        for (SubBreedObject b : list) {
            if (b.getSubBreed().contains(query)) {
                newList.add(b);
            }
        }
        return newList;
    }
}

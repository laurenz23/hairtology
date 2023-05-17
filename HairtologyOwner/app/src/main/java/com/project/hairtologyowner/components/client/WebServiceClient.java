package com.project.hairtologyowner.components.client;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient<RestClient> {

    private final Gson mGson;
    private final RestClient mRestClient;
    private final OkHttpClient mHttpClient;

    protected WebServiceClient(String baseUrl, Class<RestClient> clientClass) {
        mGson = new Gson();
        mHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();
        mRestClient = retrofit.create(clientClass);
    }

    public RestClient getRestClient() {
        return mRestClient;
    }

}

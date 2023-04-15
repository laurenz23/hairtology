package com.project.hairtologyuser.components.client;

import com.project.hairtologyuser.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApiClient {

    @GET("users/{uuid}/info")
    Call<UserModel> getUser(
            @Path("uuid") String uuid
    );

    @POST("users/{uuid}/info")
    Call<UserModel> registerUser(
            @Path("uuid") String uuid,
            @Body UserModel userModel
    );

}

package com.repairzone.cobra.API;

import com.repairzone.cobra.Object.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DatabaseAPI {
    @FormUrlEncoded
    @POST("/users_new.php")
    Call<Value> register (
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/users_login.php")
    Call<Value> login (
            @Field("username") String username,
            @Field("password") String password);
}

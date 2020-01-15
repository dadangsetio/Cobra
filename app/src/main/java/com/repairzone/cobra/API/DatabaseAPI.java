package com.repairzone.cobra.API;

import com.repairzone.cobra.Object.Item;
import com.repairzone.cobra.Object.Stock;
import com.repairzone.cobra.Object.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DatabaseAPI {
    @FormUrlEncoded
    @POST("/api/users_new.php")
    Call<Value> register (
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/users_login.php")
    Call<Value> login (
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("/api/stock_get.php")
    Call<Value> listStock();

    @FormUrlEncoded
    @POST("/api/item_new.php")
    Call<Item> addItem(
            @Field("nama") String nama,
            @Field("satuan") String satuan
    );

    @GET("/api/item_get.php")
    Call<Value> listItem();

    @FormUrlEncoded
    @POST("/api/stock_new.php")
    Call<Stock> addStock(
            @Field("nama") String nama,
            @Field("jumlah") int jumlah,
            @Field("satuan") String satuan
    );

    @FormUrlEncoded
    @POST("/api/stock_out.php")
    Call<Stock> StockOut(
            @Field("nama") String nama,
            @Field("jumlah") int jumlah,
            @Field("satuan") String satuan
    );
}

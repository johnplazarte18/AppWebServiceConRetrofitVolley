package com.example.appwebserviceconretrofit.Interface;
import com.example.appwebserviceconretrofit.Modelo.banco;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface JsonApi {
    @Headers("Public-Merchant-Id:d6d229453ee34affa819cd9014cac51b")
    @GET("transfer/v1/bankList")
    Call<List<banco>> getbanco();
}

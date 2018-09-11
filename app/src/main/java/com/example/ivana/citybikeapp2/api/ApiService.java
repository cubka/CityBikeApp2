package com.example.ivana.citybikeapp2.api;

import com.example.ivana.citybikeapp2.models.NetworksModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("networks")
    Call<NetworksModel> getNetList();

}

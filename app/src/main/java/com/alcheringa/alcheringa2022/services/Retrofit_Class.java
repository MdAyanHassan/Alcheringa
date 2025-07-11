package com.alcheringa.alcheringa2022.services;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retrofit_Class {

    @FormUrlEncoded
    @POST("formResponse")
    Call<Void> DataToExcel(
            @FieldMap Map<String,Object> map
    );

    @GET("get_data")
    Call<String> getData(@Query("email") String email);

}

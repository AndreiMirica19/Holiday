package com.example.holiday.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?&units=metric&appid=fbdf2cbab4d7a7cc6d0eba07c5628fd2")
    Call<City> getWheatherData(@Query("q") String name);

}

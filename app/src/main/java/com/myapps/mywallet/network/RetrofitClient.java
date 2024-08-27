package com.myapps.mywallet.network;


import com.myapps.mywallet.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static OkHttpClient getClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(new MyInterceptor())
                .build();
    }

    public static Retrofit getRetrofitClient(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }
}

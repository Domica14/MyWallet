package com.myapps.mywallet.model.service;

import com.myapps.mywallet.model.data.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("rest/v1/Usuario")
    Call<List<Usuario>> getUsuario(@Query("username") String username);

    @POST("rest/v1/Usuario")
    Call<List<Usuario>> newUsuario(@Body Usuario usuario);
}

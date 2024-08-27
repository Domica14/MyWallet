package com.myapps.mywallet.model.service;

import com.myapps.mywallet.model.data.ClienteResponse;
import com.myapps.mywallet.model.data.ClienteRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApiService {

    @GET("rest/v1/Clientes")
    Call<ClienteResponse> getCliente(@Query("idClient") int idClient);

    @GET("rest/v1/Clientes")
    Call<List<ClienteResponse>> getClientes();

    @POST("rest/v1/Clientes")
    Call<List<ClienteResponse>> newClient(@Body ClienteRequest client);

    @GET("rest/v1/Clientes")
    Call<List<ClienteResponse>> ifExistClientEmail(@Query("email") String email);

    @GET("rest/v1/Clientes")
    Call<List<ClienteResponse>> ifExistClientId(@Query("id") String id);
}

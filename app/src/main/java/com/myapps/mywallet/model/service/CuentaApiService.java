package com.myapps.mywallet.model.service;

import com.myapps.mywallet.model.data.CuentaRequest;
import com.myapps.mywallet.model.data.CuentaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CuentaApiService {

    @GET("rest/v1/Cuentas")
    Call<List<CuentaResponse>> getCuenta(@Query("numeroCuenta") String numeroCuenta);

    @POST("rest/v1/Cuentas")
    Call<List<CuentaResponse>> addCuenta(@Body CuentaRequest cuentaRequest);
}

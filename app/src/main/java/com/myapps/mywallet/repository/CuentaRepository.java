package com.myapps.mywallet.repository;

import android.util.Log;

import com.myapps.mywallet.model.data.CuentaRequest;
import com.myapps.mywallet.model.data.CuentaResponse;
import com.myapps.mywallet.model.service.CuentaApiService;
import com.myapps.mywallet.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CuentaRepository {

    private final CuentaApiService cuentaApiService;

    public CuentaRepository() {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        cuentaApiService = retrofit.create(CuentaApiService.class);
    }

    public void addCuenta(CuentaRequest cuentaRequest, final CuentaCallback cuentaCallback) {
        cuentaApiService.addCuenta(cuentaRequest).enqueue(new Callback<List<CuentaResponse>>() {
            @Override
            public void onResponse(Call<List<CuentaResponse>> call, Response<List<CuentaResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        cuentaCallback.onError("Ha ocurrido un error");
                    } else {
                        cuentaCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CuentaResponse>> call, Throwable throwable) {
                cuentaCallback.onError("Ha ocurrido un error");
            }
        });
    }

    public void getCuenta(String numeroCuenta, final CuentaCallback cuentaCallback) {
        cuentaApiService.getCuenta("eq."+numeroCuenta).enqueue(new Callback<List<CuentaResponse>>() {
            @Override
            public void onResponse(Call<List<CuentaResponse>> call, Response<List<CuentaResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        cuentaCallback.onError("La cuenta no existe");
                    } else {
                        cuentaCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CuentaResponse>> call, Throwable throwable) {
                cuentaCallback.onError("Ha ocurrido un error");
            }
        });
    }


    public interface CuentaCallback {
        void onSuccess(CuentaResponse cuenta);
        void onError(String error);
    }
}

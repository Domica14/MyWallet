package com.myapps.mywallet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myapps.mywallet.model.data.ClienteResponse;
import com.myapps.mywallet.model.data.ClienteRequest;
import com.myapps.mywallet.model.service.ClientApiService;
import com.myapps.mywallet.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientRepository {

    private final ClientApiService clientApiService;


    public ClientRepository() {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        clientApiService = retrofit.create(ClientApiService.class);
    }

    public void getClient(int idClient) {
        clientApiService.getCliente(idClient).enqueue(new Callback<ClienteResponse>() {
            @Override
            public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {

            }

            @Override
            public void onFailure(Call<ClienteResponse> call, Throwable throwable) {
                Log.e("ClientApiService", throwable.getMessage() ,throwable);
            }
        });
    }

    public void addClient(ClienteRequest clienteRequest, final ClientCallback clientCallback) {
        clientApiService.newClient(clienteRequest).enqueue(new Callback<List<ClienteResponse>>() {
            @Override
            public void onResponse(Call<List<ClienteResponse>> call, Response<List<ClienteResponse>> response) {
                //Se verifica que la response no sea null y que no este vacia la lista
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        clientCallback.onError("Error, el usuario ya existe");
                    }
                    else {
                        clientCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ClienteResponse>> call, Throwable throwable) {
                clientCallback.onError(throwable.getMessage());
            }
        });
    }

    public void ifExistClientEmail(String email, final ClientCallback clientCallback) {
        clientApiService.ifExistClientEmail("eq."+email).enqueue(new Callback<List<ClienteResponse>>() {
            @Override
            public void onResponse(Call<List<ClienteResponse>> call, Response<List<ClienteResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        clientCallback.onError("El correo no existe");
                    } else {
                        clientCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ClienteResponse>> call, Throwable throwable) {
                clientCallback.onError("Ha ocurrido un error");
            }
        });
    }

    public void ifExistClientId(String id, final ClientCallback clientCallback) {
        clientApiService.ifExistClientId("eq."+id).enqueue(new Callback<List<ClienteResponse>>() {
            @Override
            public void onResponse(Call<List<ClienteResponse>> call, Response<List<ClienteResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        clientCallback.onError("La cedula no existe");
                    } else {
                        clientCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ClienteResponse>> call, Throwable throwable) {
                clientCallback.onError("Ha ocurrido un error");
            }
        });
    }

    public interface ClientCallback {
        void onSuccess(ClienteResponse clienteResponse);
        void onError(String error);
    }
}

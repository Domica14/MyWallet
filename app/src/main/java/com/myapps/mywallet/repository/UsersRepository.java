package com.myapps.mywallet.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myapps.mywallet.model.data.Usuario;
import com.myapps.mywallet.model.service.UserApiService;
import com.myapps.mywallet.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersRepository {

    private final UserApiService userApiService;

    public UsersRepository() {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        userApiService = retrofit.create(UserApiService.class);
    }

    public void getUser(String username, final userCallback userCallback) {
        userApiService.getUsuario("eq."+username).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        userCallback.onError("El usuario no existe");
                    } else {
                        userCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable throwable) {
                userCallback.onError(throwable.getMessage());
            }
        });
    }

    public void addUser(Usuario usuario, final userCallback userCallback) {
        userApiService.newUsuario(usuario).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        userCallback.onError("ERROR! el usuario ya existe");
                    } else {
                        userCallback.onSuccess(response.body().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable throwable) {
                userCallback.onError("ERROR! el usuario ya existe");
            }
        });
    }

    public interface userCallback {
        void onSuccess(Usuario usuario);
        void onError(String error);
    }

}

package com.myapps.mywallet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myapps.mywallet.model.data.Usuario;
import com.myapps.mywallet.repository.UsersRepository;
import com.myapps.mywallet.utility.Hashing;

public class LoginViewModel extends ViewModel {

    private final UsersRepository usersRepository;
    private final MutableLiveData<Boolean> userExist = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LoginViewModel() {
        usersRepository = new UsersRepository();
    }

    public void validateUser(String username, String password) {
        usersRepository.getUser(username, new UsersRepository.userCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario.getUsername().equals(username) && Hashing.compareHash(password, usuario.getPassword())) {
                    userExist.setValue(true);
                } else {
                    userExist.setValue(false);
                }
            }
            @Override
            public void onError(String error) {
                    errorMessage.setValue(error);
                }
        });
    }

    public LiveData<Boolean> getUserExist() {
        return userExist;
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }
}

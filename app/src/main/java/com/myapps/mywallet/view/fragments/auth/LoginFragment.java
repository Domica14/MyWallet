package com.myapps.mywallet.view.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.mywallet.R;
import com.myapps.mywallet.viewmodel.LoginViewModel;


public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtRegistrarse = view.findViewById(R.id.Registrarse);

        //Hacer el texto touchable
        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUp);
            }
        });

        //Se crea el viewmodel provider
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
                if (modelClass.isAssignableFrom(LoginViewModel.class)) {
                    return (T) new LoginViewModel();
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(LoginViewModel.class);

        //--------------------------------Elementos de la UI------------------------------------------------------------------------

        EditText user, password;
        Button btnLogin;
        user = view.findViewById(R.id.UsuarioLogin);
        password = view.findViewById(R.id.PasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);

        //-------------------------------------------------------------------------------------------------------------------------

        //Observers
        userExistObserver(loginViewModel, view);
        errorMessageObserver(loginViewModel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.validateUser(user.getText().toString(), password.getText().toString());
            }
        });

    }

    //Observa el LiveData del viewmodel para saber si el usuario existe
    private void userExistObserver(LoginViewModel loginViewModel, View view) {
        loginViewModel.getUserExist().observe(getViewLifecycleOwner(), userResponse -> {
            if (loginViewModel.getUserExist().getValue()) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_home2);
            }
            else {
                Toast.makeText(getContext(), "Error al iniciar sesión, contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Observa el LiveData del viewmodel para saber si el usuario no existe
    private void errorMessageObserver(LoginViewModel loginViewModel) {
        loginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), userResponse -> {
            Toast.makeText(getContext(), "Error al iniciar sesión ("
                    +loginViewModel.getErrorMessage().getValue()+")", Toast.LENGTH_SHORT).show();
        });
    }
}
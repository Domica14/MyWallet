package com.myapps.mywallet.view.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.mywallet.R;
import com.myapps.mywallet.viewmodel.SignUpViewModel;


public class SignUpFragment extends Fragment {

    private SignUpViewModel signUpViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView login = view.findViewById(R.id.IniciarSesion);

        //Hacer el texto touchable
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signUp_to_loginFragment);
            }
        });


        //Se crea el viewmodelprovider para tener acceso al ViewModel.class
        signUpViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
                if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
                    return (T) new SignUpViewModel();
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(SignUpViewModel.class);


        //--------------------------------Elementos de la UI------------------------------------------------------------------------

        Button btnRegistro = view.findViewById(R.id.BtnRegistrarse);
        EditText name, id, email, celular, user, password;
        name = view.findViewById(R.id.NombreSignUp);
        id = view.findViewById(R.id.CedulaSignUp);
        email = view.findViewById(R.id.EmailSignUp);
        celular = view.findViewById(R.id.CelularSignUp);
        user = view.findViewById(R.id.UsuarioSignUp);
        password = view.findViewById(R.id.PasswordSignUp);

        //-------------------------------------------------------------------------------------------------------------------------


        signUpViewModel.getClientUserValidationStatus().observe(getViewLifecycleOwner(), status -> {
            if (status) {
                Toast.makeText(getContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                signUpViewModel.addClient(name.getText().toString(), email.getText().toString(),
                        id.getText().toString(), user.getText().toString(), password.getText() .toString(),
                        celular.getText().toString());
                Toast.makeText(getContext(), "El cliente ha sido creado exitosamente", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpViewModel.checkEmailExist(email.getText().toString());
                signUpViewModel.checkIdExists(id.getText().toString());
                signUpViewModel.checkUserExists(user.getText().toString());
                signUpViewModel.checkCuentaExists(celular.getText().toString());
                signUpViewModel.resetClientUserExistStatus();
            }
        });



    }

}
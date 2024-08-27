package com.myapps.mywallet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myapps.mywallet.model.data.ClienteRequest;
import com.myapps.mywallet.model.data.ClienteResponse;
import com.myapps.mywallet.model.data.CuentaRequest;
import com.myapps.mywallet.model.data.CuentaResponse;
import com.myapps.mywallet.model.data.Usuario;
import com.myapps.mywallet.repository.ClientRepository;
import com.myapps.mywallet.repository.CuentaRepository;
import com.myapps.mywallet.repository.UsersRepository;
import com.myapps.mywallet.utility.Hashing;

public class SignUpViewModel extends ViewModel {

    private final ClientRepository clientRepository;
    private final UsersRepository usersRepository;
    private final CuentaRepository cuentaRepository;
    private MutableLiveData<Usuario> userData = new MutableLiveData<>();
    private MutableLiveData<Boolean> clientRegistered = new MutableLiveData<>();
    private MutableLiveData<Boolean> userRegistered = new MutableLiveData<>();
    private MutableLiveData<Boolean> clientUserValidationStatus = new MutableLiveData<>();
    private Boolean clientEmailExistStatus;
    private Boolean clientIdExistStatus;
    private Boolean userExistStatus;
    private Boolean cuentaExistStatus;


    public SignUpViewModel() {
        clientRepository = new ClientRepository();
        usersRepository = new UsersRepository();
        cuentaRepository = new CuentaRepository();
    }

    //Metodo para registrar un nuevo cliente al registrarse
    public void addClient(String name, String email, String id, String user, String password, String cuenta) {
        clientRepository.addClient(new ClienteRequest(name, email, id),
                new ClientRepository.ClientCallback() {
                    @Override
                    public void onSuccess(ClienteResponse clienteResponse) {
                        clientRegistered.setValue(true);
                        //Al crearse el cliente se agrega el usuario y cuenta
                        addUser(user, Hashing.hashPassword(password), clienteResponse.getIdClient());
                        addCuenta(cuenta, clienteResponse.getIdClient());
                    }

                    @Override
                    public void onError(String error) {
                        clientRegistered.setValue(false);
                    }
                });
    }

    public void addUser(String user, String password, int idClient) {
        usersRepository.addUser(new Usuario(user, password, idClient), new UsersRepository.userCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                userRegistered.setValue(true);
                userData.setValue(usuario);
            }

            @Override
            public void onError(String error) {
                userRegistered.setValue(false);
            }
        });
    }

    public void addCuenta(String numeroCuenta, int idClient) {
        cuentaRepository.addCuenta(new CuentaRequest(numeroCuenta, idClient), new CuentaRepository.CuentaCallback() {
            @Override
            public void onSuccess(CuentaResponse cuenta) {
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    public void checkEmailExist(String email) {

        //Se verifica si existe el correo
        clientRepository.ifExistClientEmail(email, new ClientRepository.ClientCallback() {
            @Override
            public void onSuccess(ClienteResponse clienteResponse) {
                setClientEmailExistStatus(true);
                checkClientUserExists();
            }

            @Override
            public void onError(String error) {
                setClientEmailExistStatus(false);
                checkClientUserExists();
            }
        });
    }

    public void checkIdExists(String id) {
        //Se veifica si existe la cedula
        clientRepository.ifExistClientId(id, new ClientRepository.ClientCallback() {
            @Override
            public void onSuccess(ClienteResponse clienteResponse) {
                setClientIdExistStatus(true);
                checkClientUserExists();
            }

            @Override
            public void onError(String error) {
                setClientIdExistStatus(false);
                checkClientUserExists();
            }
        });
    }

    public void checkUserExists(String username) {
        //Se verifica si existe el nombre de usuario
        usersRepository.getUser(username, new UsersRepository.userCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                setUserExistStatus(true);
                checkClientUserExists();
            }

            @Override
            public void onError(String error) {
                setUserExistStatus(false);
                checkClientUserExists();
            }
        });
    }

    public void checkCuentaExists(String numeroCuenta) {
        //Se verifica si existe el numero de cuenta (celular)
        cuentaRepository.getCuenta(numeroCuenta, new CuentaRepository.CuentaCallback() {
            @Override
            public void onSuccess(CuentaResponse cuentaResponse) {
                setCuentaExistStatus(true);
                checkClientUserExists();
            }

            @Override
            public void onError(String error) {
                setCuentaExistStatus(false);
                checkClientUserExists();
            }
        });
    }

    //Verificacion de status de cliente y usuario
    public void checkClientUserExists() {
        Boolean clientEmailStatus = getClientEmailExistStatus();
        Boolean clientIdStatus = getClientIdExistStatus();
        Boolean userStatus = getUserExistStatus();
        Boolean cuentaStatus = getCuentaExistStatus();

        if (clientEmailStatus != null && userStatus != null && clientIdStatus != null && cuentaStatus != null) {
            //Si uno de los dos es cierto, devolveria cierto, que existe
            clientUserValidationStatus.setValue(clientEmailStatus || userStatus || clientIdStatus || cuentaStatus);
        }
    }

    public void resetClientUserExistStatus() {
        //Se resetean los valores para evitar problemas
        setClientEmailExistStatus(null);
        setClientIdExistStatus(null);
        setUserExistStatus(null);
        setCuentaExistStatus(null);
    }

    public LiveData<Boolean> getClientRegistered() {
        return clientRegistered;
    }

    public LiveData<Boolean> getUserRegistered() {
        return userRegistered;
    }

    public LiveData<Usuario> getUserData() {
        return userData;
    }

    private Boolean getUserExistStatus() {
        return userExistStatus;
    }

    private void setUserExistStatus(Boolean userExistStatus) {
        this.userExistStatus = userExistStatus;
    }

    private Boolean getClientEmailExistStatus() {
        return clientEmailExistStatus;
    }

    private void setClientEmailExistStatus(Boolean clientEmailExistStatus) {
        this.clientEmailExistStatus = clientEmailExistStatus;
    }

    private Boolean getClientIdExistStatus() {
        return clientIdExistStatus;
    }

    private void setClientIdExistStatus(Boolean clientIdExistStatus) {
        this.clientIdExistStatus = clientIdExistStatus;
    }

    private Boolean getCuentaExistStatus() {
        return cuentaExistStatus;
    }

    private void setCuentaExistStatus(Boolean cuentaExistStatus) {
        this.cuentaExistStatus = cuentaExistStatus;
    }

    public LiveData<Boolean> getClientUserValidationStatus() {
        return clientUserValidationStatus;
    }

}

package com.myapps.mywallet.model.data;

public class Usuario {

    private String username;
    private String password;
    private int idClient;

    public Usuario() {}

    public Usuario(String username, String password, int idClient) {
        this.username = username;
        this.password = password;
        this.idClient = idClient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

}

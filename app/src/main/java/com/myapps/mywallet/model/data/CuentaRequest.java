package com.myapps.mywallet.model.data;

public class CuentaRequest {

    private String numeroCuenta;
    private int idClient;

    public CuentaRequest() {}

    public CuentaRequest(String numeroCuenta, int idClient) {
        this.numeroCuenta = numeroCuenta;
        this.idClient = idClient;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}

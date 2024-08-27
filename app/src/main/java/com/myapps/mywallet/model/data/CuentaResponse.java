package com.myapps.mywallet.model.data;

public class CuentaResponse {

    private String numeroCuenta;
    private float saldo;
    private int idCliente;

    public CuentaResponse() {}

    public CuentaResponse(String numeroCuenta, float saldo, int idCliente) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.idCliente = idCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}


package com.myapps.mywallet.model.data;

public class ClienteResponse {

    private int idClient;
    private String name;
    private String email;
    private String id;

    public ClienteResponse(){}

    public ClienteResponse(int idClient, String name, String email, String id){
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

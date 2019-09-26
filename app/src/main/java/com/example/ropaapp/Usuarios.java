package com.example.ropaapp;

public class Usuarios {
    private String idUsuario;
    private String perfil;
    private String contraseña;

    public String idUsuario() {
        return idUsuario;
    }

    public void idUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String perfil() {
        return perfil;
    }

    public void perfil(String perfil) {
        this.perfil = perfil;
    }

    public String contraseña() {
        return contraseña;
    }

    public void contraseña(String contraseña) {
        this.contraseña = contraseña;
    }


}

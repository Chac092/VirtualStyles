package com.example.ropaapp;

import java.util.Objects;

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

    public void Usuarios(){

    }

    public void Usuarios(String idUsuario, String perfil, String contraseña){
        this.idUsuario=idUsuario;
        this.perfil=perfil;
        this.contraseña=contraseña;
    }


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return Objects.equals(idUsuario, usuarios.idUsuario) &&
                Objects.equals(perfil, usuarios.perfil) &&
                Objects.equals(contraseña, usuarios.contraseña);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, perfil, contraseña);
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "idUsuario='" + idUsuario + '\'' +
                ", perfil='" + perfil + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}

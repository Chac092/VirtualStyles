package com.example.ropaapp;

import java.util.Objects;

public class Prenda {
    private int id;
    private String categoria;
    private String estilo;
    private boolean estado;
    private boolean favorito;
    private int idUsuario;

    public void Prenda(){

    }

    public void Prenda(int id, String categoria, String estilo, boolean estado, boolean favorito, int idUsuario){
        this.id=id;
        this.categoria=categoria;
        this.estilo=estilo;
        this.estado=estado;
        this.favorito=favorito;
        this.idUsuario=idUsuario;
    }

    public int id() {
        return id;
    }

    public void id(int id) {
        this.id = id;
    }

    public String categoria() {
        return categoria;
    }

    public void categoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean estado() {
        return estado;
    }

    public void estado(boolean estado) {
        this.estado = estado;
    }

    public boolean favorito() {
        return favorito;
    }

    public void favorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String estilo() {
        return estilo;
    }

    public void estilo(String estilo) {
        this.estilo = estilo;
    }

    public int idUsuario() {
        return idUsuario;
    }

    public void idUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prenda prenda = (Prenda) o;
        return id == prenda.id &&
                estado == prenda.estado &&
                favorito == prenda.favorito &&
                idUsuario == prenda.idUsuario &&
                Objects.equals(categoria, prenda.categoria) &&
                Objects.equals(estilo, prenda.estilo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria, estilo, estado, favorito, idUsuario);
    }

    @Override
    public String toString() {
        return "Prenda{" +
                "id=" + id +
                ", categoria='" + categoria + '\'' +
                ", estilo='" + estilo + '\'' +
                ", estado=" + estado +
                ", favorito=" + favorito +
                ", idUsuario=" + idUsuario +
                '}';
    }
}

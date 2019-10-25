package com.example.ropaapp;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Conjunto {
    private int idConjunto;
    private int prenda1;
    private int prenda2;
    private int prenda3;
    private int prenda4;
    private String IdUsuario;

    public Conjunto(int id, int prenda1, int prenda2, int prenda3, int prenda4, String idUsuario) {
        this.idConjunto=idConjunto;
        this.prenda1=prenda1;
        this.prenda2=prenda2;
        this.prenda2=prenda3;
        this.prenda2=prenda4;
        this.IdUsuario = idUsuario;
    }

    public void Conjunto(){

    }

    /*public void Conjunto(int idConjunto, int prenda1, int prenda2, int prenda3, int prenda4,String usuario){
        this.idConjunto=idConjunto;
        this.prenda1=prenda1;
        this.prenda2=prenda2;
        this.prenda2=prenda3;
        this.prenda2=prenda4;
        this.IdUsuario = usuario;
    }*/

    public int idConjunto() {
        return idConjunto;
    }

    public void idConjunto(int idConjunto) {
        this.idConjunto = idConjunto;
    }

    public int prenda1() {
        return prenda1;
    }

    public void prenda1(int prenda1) {
        this.prenda1 = prenda1;
    }
    public int prenda2() {
        return prenda2;
    }

    public void prenda2(int prenda2) {
        this.prenda2 = prenda2;
    }

    public int prenda3() {
        return prenda3;
    }

    public void prenda3(int prenda3) {
        this.prenda3 = prenda3;
    }

    public int prenda4() {
        return prenda4;
    }
    public void setIdUsuario(String idUsuario) {
        this.IdUsuario = idUsuario;
    }
    public String getIdUsuario() {
        return IdUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conjunto conjunto = (Conjunto) o;
        return idConjunto == conjunto.idConjunto &&
                prenda1 == conjunto.prenda1 &&
                prenda2 == conjunto.prenda2 &&
                prenda3 == conjunto.prenda3 &&
                prenda4 == conjunto.prenda4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConjunto, prenda1, prenda2, prenda3, prenda4);
    }

    @Override
    public String toString() {
        return "Conjunto{" +
                "idConjunto=" + idConjunto +
                ", prenda1=" + prenda1 +
                ", prenda2=" + prenda2 +
                ", prenda3=" + prenda3 +
                ", prenda4=" + prenda4 +
                '}';
    }
    public void GenerarTodosConjuntos(){


    }
}


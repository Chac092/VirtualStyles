package com.example.ropaapp;

import java.util.Objects;

public class Conjunto {
    private int idConjunto;
    private int prenda1;
    private int prenda2;
    private int prenda3;
    private int prenda4;
    private String idUsuario;

    public void Conjunto(){

    }

    public Conjunto(int idConjunto, int prenda1, int prenda2, int prenda3, int prenda4, String idUsuario) {
        this.idConjunto = idConjunto;
        this.prenda1 = prenda1;
        this.prenda2 = prenda2;
        this.prenda3 = prenda3;
        this.prenda4 = prenda4;
        this.idUsuario = idUsuario;
    }

    public void Conjunto(int idConjunto, int prenda1, int prenda2, int prenda3, int prenda4){
        this.idConjunto=idConjunto;
        this.prenda1=prenda1;
        this.prenda2=prenda2;
        this.prenda2=prenda3;
        this.prenda2=prenda4;
    }

    public int getIdConjunto() {
        return idConjunto;
    }

    public int getPrenda1() {
        return prenda1;
    }

    public int getPrenda2() {
        return prenda2;
    }

    public int getPrenda3() {
        return prenda3;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdConjunto(int idConjunto) {
        this.idConjunto = idConjunto;
    }

    public void setPrenda1(int prenda1) {
        this.prenda1 = prenda1;
    }

    public void setPrenda2(int prenda2) {
        this.prenda2 = prenda2;
    }

    public void setPrenda3(int prenda3) {
        this.prenda3 = prenda3;
    }

    public void setPrenda4(int prenda4) {
        this.prenda4 = prenda4;
    }

    public int getPrenda4() {
        return prenda4;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conjunto conjunto = (Conjunto) o;
        return  prenda1 == conjunto.prenda1 &&
                prenda2 == conjunto.prenda2 &&
                prenda3 == conjunto.prenda3 &&
                prenda4 == conjunto.prenda4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prenda1, prenda2, prenda3, prenda4);
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
}


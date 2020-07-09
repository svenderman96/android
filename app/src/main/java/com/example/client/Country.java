package com.example.client;

public class Country
{
    private String name;
    private int oboleli;
    private int preminuli;
    private int izleceni;

    public Country(String name, int oboleli, int preminuli, int izleceni)
    {
        this.name = name;
        this.oboleli = oboleli;
        this.preminuli = preminuli;
        this.izleceni = izleceni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOboleli() {
        return oboleli;
    }

    public void setOboleli(int oboleli) {
        this.oboleli = oboleli;
    }

    public int getPreminuli() {
        return preminuli;
    }

    public void setPreminuli(int preminuli) {
        this.preminuli = preminuli;
    }

    public int getIzleceni() {
        return izleceni;
    }

    public void setIzleceni(int izleceni) {
        this.izleceni = izleceni;
    }

    public Country() {
    }
}

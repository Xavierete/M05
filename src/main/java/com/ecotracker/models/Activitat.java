package com.ecotracker.models;

import java.time.LocalDate;

public class Activitat {
    private int id;
    private String nom;
    private LocalDate data;
    private String categoria;
    private String descripcio;
    private double co2Estalviat;

    public Activitat() {
    }

    public Activitat(String nom, LocalDate data, String categoria, String descripcio, double co2Estalviat) {
        this.nom = nom;
        this.data = data;
        this.categoria = categoria;
        this.descripcio = descripcio;
        this.co2Estalviat = co2Estalviat;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getCo2Estalviat() {
        return co2Estalviat;
    }

    public void setCo2Estalviat(double co2Estalviat) {
        this.co2Estalviat = co2Estalviat;
    }

    @Override
    public String toString() {
        return "Activitat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", data=" + data +
                ", categoria='" + categoria + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", co2Estalviat=" + co2Estalviat +
                '}';
    }
} 
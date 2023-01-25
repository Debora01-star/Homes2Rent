package com.Homes2Rent.Homes2Rent.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;


@Entity
@Table(name = "woningen")
public class Woning {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String type;
    private String name;
    private Integer price;
    private String rented;


    @JoinColumn(name = "klant_id")
    private String klant;


    @OneToOne(mappedBy = "woning")
    @JsonBackReference
    private Boeking boeking;

    public Woning(Long id, String type, String name, Integer price, String rented) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.rented = rented;
    }

    public Woning(Long id, String type, String name, String klant) {
        this.klant = klant;
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public Woning(String type, String name, Long id, Integer price, String rented, String klant) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.price = price;
        this.rented = rented;
        this.klant = klant;
    }

    public Woning() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRented() {
        return rented;
    }

    public void setRented(String rented) {
        this.rented = rented;
    }

    public String getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = String.valueOf(klant);
    }

    public Boeking getBoeking() {
        return boeking;
    }

    public void setBoeking(Boeking boeking) {
        this.boeking = boeking;
    }
}


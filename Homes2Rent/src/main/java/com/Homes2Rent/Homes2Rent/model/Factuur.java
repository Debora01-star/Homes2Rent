package com.Homes2Rent.Homes2Rent.model;


import javax.persistence.*;

@Entity
@Table(name= "facturen")

public class Factuur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String klant;

    private String boeking;

    private Integer price;


    public Factuur(Long id, String klant, String boeking, Integer price) {

        this.id = id;
        this.klant = klant;
        this.boeking = boeking;
        this.price = price;


    }

    public Factuur() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKlant() {
        return klant;
    }

    public void setKlant(String klant) {
        this.klant = klant;
    }

    public String getBoeking() {
        return boeking;
    }

    public void setBoeking(String boeking) {
        this.boeking = boeking;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
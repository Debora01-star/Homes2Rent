package com.Homes2Rent.Homes2Rent.dto;


import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.model.Klant;

public class FactuurInputDto {

    public Long id;

    public String klant;

    public String boeking;

    public Integer price;

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


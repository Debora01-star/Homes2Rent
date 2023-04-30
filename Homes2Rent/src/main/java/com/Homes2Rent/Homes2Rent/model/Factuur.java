package com.Homes2Rent.Homes2Rent.model;
import javax.persistence.*;



@Entity
public class Factuur {

    @Id
    @GeneratedValue
    private Long id;
    private String klant;
    private Integer price;

    @OneToOne(mappedBy = "factuur")
    Boeking boeking;


    public Factuur(Long id, String klant, Integer price) {

        this.id = id;
        this.klant = klant;
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


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Factuur getFactuur() {
        return new Factuur();
    }
}
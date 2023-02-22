package com.Homes2Rent.Homes2Rent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name="boekingen")
public class Boeking {


    @Id
    @GeneratedValue
    private Long id;
    private LocalDate finish_date;
    private String status;
    private String type_boeking;

    public Integer price;

    @ManyToOne
    @JoinColumn(name = "woning_id")
    Woning woning;

    public Woning getWoning() {
        return woning;
    }

    public Factuur getFactuur() {
        return factuur;
    }

    public void setFactuur(Factuur factuur) {
        this.factuur = factuur;
    }

    public Annulering getAnnulering() {
        return annulering;
    }

    public void setAnnulering(Annulering annulering) {
        this.annulering = annulering;
    }



    @OneToOne
    @JoinColumn(name ="factuur")
    Factuur factuur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "annulering_id")
    private Annulering annulering;


    public Boeking(Long id, LocalDate finish_date, String status, String type_boeking, Integer price, Woning woning) {
        this.id = id;
        this.finish_date = finish_date;
        this.status = status;
        this.type_boeking = type_boeking;
        this.price = price;
        this.woning = woning;
    }

    public Boeking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(LocalDate finish_date) {
        this.finish_date = finish_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType_boeking() {
        return type_boeking;
    }

    public void setType_boeking(String type_boeking) {
        this.type_boeking = type_boeking;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAnnulering(Object annulering) {
    }

    public Woning getWoning(Woning woning) {
        return woning;
    }

    public void setWoning(Woning woning) { this.woning = woning;
    }

}
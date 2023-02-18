package com.Homes2Rent.Homes2Rent.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="boekingen")
public class Boeking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate finish_date;
    private String notes;
    private String status;
    private String type_boeking;


    @JsonManagedReference
    public String woning;

    public Integer price;

    public Boeking(Long id, LocalDate finish_date, String notes, String status, String type_boeking, String woning, Integer price) {
        this.id = id;
        this.finish_date = finish_date;
        this.notes = notes;
        this.status = status;
        this.type_boeking = type_boeking;
        this.woning = woning;
        this.price = price;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getWoning() {
        return woning;
    }

    public void setWoning(String woning) {
        this.woning = woning;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
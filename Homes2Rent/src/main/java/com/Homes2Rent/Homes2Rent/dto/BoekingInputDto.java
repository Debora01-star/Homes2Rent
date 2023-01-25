package com.Homes2Rent.Homes2Rent.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.Homes2Rent.Homes2Rent.model.Woning;

import javax.persistence.Column;
import java.time.LocalDate;

public class BoekingInputDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)

    public LocalDate finish_date;

    public Long id;

    public String notes;

    public String status;

    public String type_boeking;

    @Column(unique = true)
    public String woning;

    public Integer price;


    public BoekingInputDto(LocalDate finish_date, Long id, String notes, String status, String type_boeking, String woning, Integer price) {
        this.finish_date = finish_date;
        this.id = id;
        this.notes = notes;
        this.status = status;
        this.type_boeking = type_boeking;
        this.woning = woning;
        this.price = price;

    }

    public BoekingInputDto() {

    }

    public LocalDate getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(LocalDate finish_date) {
        this.finish_date = finish_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package com.Homes2Rent.Homes2Rent.dto;
import com.Homes2Rent.Homes2Rent.model.Woning;
import java.time.LocalDate;


public class BoekingDto {


    public Long id;
    public LocalDate finish_date;

    public String status;
    public String type_boeking;

    public Woning woning;
    private Integer price;


    public BoekingDto() {
    }

    public BoekingDto(Long id, LocalDate finish_date, String status, String type_boeking, Woning woning, Integer price) {
        this.id = id;
        this.finish_date = finish_date;
        this.status = status;
        this.type_boeking = type_boeking;
        this.woning = woning;
        this.price = price;
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

    public Woning getWoning() {
        return woning;
    }

    public void setWoning(Woning woning) {
        this.woning = woning;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setFactuurDto(FactuurDto transferToDto) {
    }

    public void setAnnuleringDto(AnnuleringDto transferToDto) {
    }
}


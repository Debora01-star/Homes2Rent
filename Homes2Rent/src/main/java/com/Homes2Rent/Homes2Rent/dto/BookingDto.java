package com.Homes2Rent.Homes2Rent.dto;
import com.Homes2Rent.Homes2Rent.model.Home;

import java.time.LocalDate;


public class BookingDto {


    public Long id;
    public LocalDate finish_date;

    public String status;
    public String type_booking;

    public Home home;
    private Integer price;


    public BookingDto() {
    }

    public BookingDto(Long id, LocalDate finish_date, String status, String type_booking, Home home, Integer price) {
        this.id = id;
        this.finish_date = finish_date;
        this.status = status;
        this.type_booking = type_booking;
        this.home = home;
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

    public String getType_booking() {
        return type_booking;
    }

    public void setType_booking(String type_booking) {
        this.type_booking = type_booking;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setReceiptDto(ReceiptDto transferToDto) {
    }

    public void setCancellationDto(CancellationDto transferToDto) {
    }
}


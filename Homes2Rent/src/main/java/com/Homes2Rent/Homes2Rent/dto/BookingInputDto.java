package com.Homes2Rent.Homes2Rent.dto;
import com.Homes2Rent.Homes2Rent.model.Home;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.Column;
import java.time.LocalDate;


public class BookingInputDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)

    public LocalDate finish_date;

    public Long id;

    public String status;

    public String type_booking;

    @Column(unique = true)
    public Home home;

    public Integer price;


    public BookingInputDto(LocalDate finish_date, Long id, String status, String type_booking, Home home, Integer price) {
        this.finish_date = finish_date;
        this.id = id;
        this.status = status;
        this.type_booking = type_booking;
        this.home = home;
        this.price = price;

    }

    public BookingInputDto() {

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
}

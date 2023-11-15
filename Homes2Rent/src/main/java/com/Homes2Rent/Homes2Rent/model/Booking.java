package com.Homes2Rent.Homes2Rent.model;
import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Table(name="booking")
public class Booking {


    @Id
    @GeneratedValue
    private Long id;
    private LocalDate finish_date;
    private String status;
    private String type_booking;

    public Integer price;

    @ManyToOne
    @JoinColumn(name = "home_id")
    Home home;

    public Home getHome() {
        return home;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }


    @OneToOne
    @JoinColumn(name = "receipt")
    Receipt receipt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cancellation_id")
    private Cancellation cancellation;


    public Booking(Long id, LocalDate finish_date, String status, String type_booking, Integer price, Home home) {
        this.id = id;
        this.finish_date = finish_date;
        this.status = status;
        this.type_booking = type_booking;
        this.price = price;
        this.home = home;
    }

    public Booking() {
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


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCancellation(Object cancellation) {
    }

    public Home getHome(Home home) {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }




}


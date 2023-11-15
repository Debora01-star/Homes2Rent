//package com.Homes2Rent.Homes2Rent.model;
//
//
//import javax.persistence.*;
//
//@Entity
//public class BoekingWoning {
//
//    @EmbeddedId
//    private BoekingWoningKey id;
//
//    @ManyToOne
//    @MapsId("boekingId")
//    @JoinColumn(name = "boeking_id")
//    private Booking booking;
//
//    @ManyToOne
//    @MapsId("woningId")
//    @JoinColumn(name = "woning_id")
//    private Home home;
//
//
//    public BoekingWoningKey getId() {
//        return id;
//    }
//
//    public void setId(BoekingWoningKey id) {
//        this.id = id;
//    }
//
//    public Booking getBoeking() {
//        return booking;
//    }
//
//    public void setBoeking(Booking booking) {
//        this.booking = booking;
//    }
//
//    public Home getWoning() {
//        return home;
//    }
//
//    public void setWoning(Home home) {
//        this.home = home;
//    }
//}

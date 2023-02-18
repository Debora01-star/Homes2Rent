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
//    private Boeking boeking;
//
//    @ManyToOne
//    @MapsId("woningId")
//    @JoinColumn(name = "woning_id")
//    private Woning woning;
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
//    public Boeking getBoeking() {
//        return boeking;
//    }
//
//    public void setBoeking(Boeking boeking) {
//        this.boeking = boeking;
//    }
//
//    public Woning getWoning() {
//        return woning;
//    }
//
//    public void setWoning(Woning woning) {
//        this.woning = woning;
//    }
//}

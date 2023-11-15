//package com.Homes2Rent.Homes2Rent.repository;
//
//import com.Homes2Rent.Homes2Rent.model.Booking;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoning;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoningKey;
//import com.Homes2Rent.Homes2Rent.model.Home;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Collection;
//
//public interface BoekingWoningRepository extends JpaRepository<BoekingWoning, BoekingWoningKey> {
//
//    Collection<BoekingWoning> findAllByBoekingId(Long boekingId);
//
//    Collection<BoekingWoning> findAllByWoningId(Long woningId);
//
//    Booking getBoeking();
//
//    Home getWoning();
//
//    void setBoeking(Booking boeking);
//
//    void setWoning(Home home);
//}
//

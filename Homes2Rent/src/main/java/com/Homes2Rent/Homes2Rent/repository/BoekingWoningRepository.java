//package com.Homes2Rent.Homes2Rent.repository;
//
//import com.Homes2Rent.Homes2Rent.model.Boeking;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoning;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoningKey;
//import com.Homes2Rent.Homes2Rent.model.Woning;
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
//    Boeking getBoeking();
//
//    Woning getWoning();
//
//    void setBoeking(Boeking boeking);
//
//    void setWoning(Woning woning);
//}
//

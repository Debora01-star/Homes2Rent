package com.Homes2Rent.Homes2Rent.repository;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository

    public interface BoekingRepository extends JpaRepository<Boeking, Long> {

//    List<Boeking> findAllBoekingenByReservationEqualsIgnoreCase(String reservation);

    }


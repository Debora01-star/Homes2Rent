package com.Homes2Rent.Homes2Rent.repository;
import com.Homes2Rent.Homes2Rent.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository

    public interface BookingRepository extends JpaRepository<Booking, Long> {

//    List<Booking> findAllBoekingenByReservationEqualsIgnoreCase(String reservation);

    }


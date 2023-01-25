package com.Homes2Rent.Homes2Rent.repository;

import com.Homes2Rent.Homes2Rent.model.Boeking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

    public interface BoekingRepository extends CrudRepository<Boeking, Long> {
    }


package com.Homes2Rent.Homes2Rent.repository;

import com.Homes2Rent.Homes2Rent.model.Factuur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Repository
public interface FactuurRepository extends JpaRepository<Factuur, Long> {

}

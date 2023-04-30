package com.Homes2Rent.Homes2Rent.repository;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FactuurRepository extends JpaRepository<Factuur, Long> {

}

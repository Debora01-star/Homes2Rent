package com.Homes2Rent.Homes2Rent.repository;
import com.Homes2Rent.Homes2Rent.model.Annulering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AnnuleringRepository extends JpaRepository<Annulering, Long> {


}

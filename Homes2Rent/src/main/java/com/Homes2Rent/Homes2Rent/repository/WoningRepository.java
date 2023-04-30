package com.Homes2Rent.Homes2Rent.repository;
import com.Homes2Rent.Homes2Rent.model.Woning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository

public interface WoningRepository extends JpaRepository<Woning, Long> {

    boolean existsById(Long Id);

    Woning findWoningById(Long Id);


}

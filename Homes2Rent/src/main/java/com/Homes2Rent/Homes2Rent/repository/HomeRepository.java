package com.Homes2Rent.Homes2Rent.repository;
import com.Homes2Rent.Homes2Rent.model.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository

public interface HomeRepository extends JpaRepository<Home, Long> {

    boolean existsById(Long Id);

    Home findHomeById(Long Id);



}

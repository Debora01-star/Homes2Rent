package com.Homes2Rent.Homes2Rent.repository;

import com.Homes2Rent.Homes2Rent.model.Factuur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public interface FactuurRepository extends CrudRepository<Factuur, Long> {
    List<Factuur> findAllFacturenByIdEqualsIgnoreCase(String id);
}

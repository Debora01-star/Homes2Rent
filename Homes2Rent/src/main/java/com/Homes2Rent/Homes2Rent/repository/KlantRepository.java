package com.Homes2Rent.Homes2Rent.repository;

import com.Homes2Rent.Homes2Rent.dto.KlantDto;
import com.Homes2Rent.Homes2Rent.model.Klant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

    public interface KlantRepository extends CrudRepository<Klant, Long> {


    boolean existsByEmail(String email);

    Klant findKlantByEmail(String email);

    List<KlantDto> getAllKlanten();
}


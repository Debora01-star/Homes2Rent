package com.Homes2Rent.Homes2Rent.repository;


import org.eclipse.sisu.plexus.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

    public interface RolRepository extends JpaRepository<Roles, Long> {
    }


package com.app.repository;

import com.app.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Integer> {
    @Query("select realEstate from RealEstate realEstate where realEstate.deleted = false" )
    List<RealEstate> findAll();
}

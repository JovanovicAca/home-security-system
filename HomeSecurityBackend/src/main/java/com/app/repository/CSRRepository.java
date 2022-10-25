package com.app.repository;

import com.app.model.CSR;
import com.app.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CSRRepository extends JpaRepository<CSR, Integer> {

    @Query("select c from CSR c where c.status = 0")
    List<CSR> findAll();
    List<CSR> findByStatus(Status status);

    @Query("select c from CSR c where c.commonName=?1")
    CSR findBycommonName(String alias);
}

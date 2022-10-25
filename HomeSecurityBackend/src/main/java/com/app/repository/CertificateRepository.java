package com.app.repository;

import com.app.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    Certificate findBySerialNumber(BigInteger serialNumber);
    @Query("select c from Certificate c where c.isRevoked = false")
    List<Certificate> findAll();
}

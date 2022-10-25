package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date startDate;
    private Date endDate;
    private String commonName;
    private String organizationUnit;
    private String organizationName;
    private String localityName;
    private String stateName;
    private String country;
    private String email;
    private boolean isValid;
    private boolean isRevoked;
    private BigInteger serialNumber;

    public Certificate(Date startDate, Date endDate, String commonName, String organizationUnit,
                       String organizationName, String localityName, String stateName, String country, String email,
                       boolean isValid, boolean isRevoked, BigInteger serialNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.commonName = commonName;
        this.organizationUnit = organizationUnit;
        this.organizationName = organizationName;
        this.localityName = localityName;
        this.stateName = stateName;
        this.country = country;
        this.email = email;
        this.isValid = isValid;
        this.isRevoked = isRevoked;
        this.serialNumber = serialNumber;
    }
}

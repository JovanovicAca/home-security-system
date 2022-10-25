package com.app.model;

import com.app.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class CSR {

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
    private Status status;
    private String algorithm = "RSA";
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CertificateExtension> extensions;
    private boolean approved;
}

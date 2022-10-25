package com.app.controller;

import com.app.model.Certificate;
import com.app.service.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/certificate")
public class CertificateController {

    private final ICertificateService certificateService;

    @Autowired
    public CertificateController(ICertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCertificates() {
        return new ResponseEntity<>(certificateService.findAllCertificates(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("{serialNum}/{reason}/{email}")
    public ResponseEntity<?> revokeCertificate(@PathVariable BigInteger serialNum, @PathVariable String reason, @PathVariable String email) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        Certificate cert = certificateService.revokeCertificate(serialNum, reason, email);
        return new ResponseEntity<>(cert,HttpStatus.OK);
    }

}


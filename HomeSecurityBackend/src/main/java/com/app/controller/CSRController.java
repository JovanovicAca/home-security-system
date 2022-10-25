package com.app.controller;

import com.app.model.CSR;
import com.app.service.ICSRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/csr")
public class CSRController {

    private final ICSRService csrService;

    @Autowired
    public CSRController(ICSRService csrService) {
        this.csrService = csrService;
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateCSR(@RequestBody CSR csr) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        CSR created_csr = csrService.createCSR(csr);
        if(created_csr == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created_csr,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCSR(@RequestBody CSR csr) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        CSR update_csr = csrService.updateCSR(csr);
        if(update_csr == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update_csr,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPendingCSR() {
        return new ResponseEntity<>(csrService.findAllCSR(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptCSR(@PathVariable Integer id) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {
        CSR csr = csrService.acceptCSR(id);
        return new ResponseEntity<>(csr,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/decline/{id}")
    public ResponseEntity<?> declineCSR(@PathVariable Integer id) {
        CSR csr = csrService.declineCSR(id);
        return new ResponseEntity<>(csr,HttpStatus.OK);
    }
}

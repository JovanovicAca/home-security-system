package com.app.service;

import com.app.model.CSR;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

public interface ICSRService {

    List<CSR> findAllCSR();
    CSR createCSR(CSR csr);
    CSR updateCSR(CSR csr);
    CSR acceptCSR(Integer id) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException;
    CSR declineCSR(Integer id);
}

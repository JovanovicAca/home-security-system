package com.app.service;
import com.app.model.CSR;
import com.app.model.Certificate;
import com.app.pki.data.IssuerData;
import com.app.pki.data.SubjectData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;

public interface ICertificateService {

    List<Certificate> findAllCertificates();
    void createCertificate(CSR csr) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException;
    IssuerData generateIssuerData(PrivateKey privateKey) throws FileNotFoundException, IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException;
    SubjectData generateSubjectData(CSR csr) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException;
    KeyPair generateKeyPair() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException;
    void saveCertificate(CSR csr, BigInteger serialNumber);
    void verify(List<Certificate> certificates);
    Certificate revokeCertificate(BigInteger serialNumber, String reason, String email) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException;
    void generateIssuerKeyPair() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException;

}

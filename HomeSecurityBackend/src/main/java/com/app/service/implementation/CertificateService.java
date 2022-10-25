package com.app.service.implementation;

import com.app.model.CSR;
import com.app.pki.certificates.CertificateGenerator;
import com.app.pki.data.IssuerData;
import com.app.pki.data.SubjectData;
import com.app.repository.CSRRepository;
import com.app.repository.CertificateRepository;
import com.app.service.ICertificateService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
public class CertificateService implements ICertificateService {

    private KeyPair keyPairIssuer;
    KeyStore keyStore;
    {
        try {
            keyStore = KeyStore.getInstance("JKS");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
    private final String PATH_LOAD = "keystorecertificate.jks";
    private final char[] password = "changeit".toCharArray();
    private IssuerData issuerData;
    private X509Certificate[] certificateChain;
    private final CertificateRepository certificateRepository;
    private final CSRRepository csrRepository;
    private final EmailServiceImpl emailService;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository, CSRRepository csrRepository, EmailServiceImpl emailService) {
        this.certificateRepository = certificateRepository;
        this.csrRepository = csrRepository;
        this.emailService = emailService;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void generateIssuerKeyPair() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        loadKeyStore(keyStore, "null");
        keyPairIssuer = generateKeyPair();
        issuerData = generateIssuerData(keyPairIssuer.getPrivate());

    }

    private void loadKeyStore(KeyStore keyStore, String path) throws CertificateException, IOException, NoSuchAlgorithmException {
        if(path.equals("null")){
            keyStore.load(null, password);
            try (FileOutputStream fos = new FileOutputStream(PATH_LOAD)) {
                keyStore.store(fos, password);
            }catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<com.app.model.Certificate> findAllCertificates() {
        List<com.app.model.Certificate> certificates = certificateRepository.findAll();
        verify(certificates);
        return certificates;
    }

    @Override
    public void verify(List<com.app.model.Certificate> certificates) {
        for (com.app.model.Certificate certificate: certificates) {
            //System.out.println(certificate.getEndDate().compareTo(new Date()));
            if (certificate.getEndDate().compareTo(new Date()) < 0 && !certificate.isValid() && !certificate.isRevoked()) {
                certificate.setValid(false);
                certificateRepository.save(certificate);
            }
        }
    }

    @Override
    public com.app.model.Certificate revokeCertificate(BigInteger serialNumber, String reason, String email) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        String commonName = null;
        keyStore.load(new FileInputStream(PATH_LOAD), password);
        Enumeration enumeration = keyStore.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = (String)enumeration.nextElement();
            try{
                 commonName = alias.split(",")[0].split("=")[1];
            } catch (Exception e) {

            }
            sendReason(email, reason);
//            CSR csr = csrRepository.findBycommonName(commonName);
//            try{
//                String csrEmail = csr.getEmail();
//
//            } catch (Exception e) {
//
//            }


            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            BigInteger serialNum = certificate.getSerialNumber();
            if(Objects.equals(serialNum, serialNumber)){
                keyStore.deleteEntry(alias);
//                writePrivateKey(issuerData.getX500name().toString(), issuerData.getPrivateKey(), password, certificate);
                try (FileOutputStream fos = new FileOutputStream(PATH_LOAD)) {
                    keyStore.store(fos, password);
                }
                com.app.model.Certificate cert = certificateRepository.findBySerialNumber(serialNum);
                cert.setValid(false);
                cert.setRevoked(true);
                return certificateRepository.save(cert);
            }
        }
        return null;
    }

    private void sendReason(String csrEmail, String reason) {
        emailService.sendMail(csrEmail, "Certificate revoked", reason);
    }

    private String findAliasBySerialNumber(BigInteger serialNumber) {
        return null;
    }

    @Override
    public void saveCertificate(CSR csr, BigInteger serialNumber) {
        boolean isValid = true;
        if (csr.getEndDate().compareTo(new Date()) < 0) {
            isValid = false;
        }

        com.app.model.Certificate certificate = new com.app.model.Certificate(csr.getStartDate(), csr.getEndDate(),
                csr.getCommonName(), csr.getOrganizationUnit(), csr.getOrganizationName(), csr.getLocalityName(),
                csr.getStateName(), csr.getCountry(), csr.getEmail(), isValid, false, serialNumber);
        certificateRepository.save(certificate);
    }

    @Override
    public void createCertificate(CSR csr) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        SubjectData subjectData = generateSubjectData(csr);

        CertificateGenerator certificateGenerator = new CertificateGenerator();
        X509Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData,
                csr.getExtensions(), subjectData.getPublicKey());
    
        keyStore.load(new FileInputStream(PATH_LOAD), password);
        //keyStore.setCertificateEntry(subjectData.getX500name().toString(), certificate);
        certificateChain = new X509Certificate[1];
        certificateChain[0] = certificate;
        //writePrivateKey(issuerData.getX500name().toString(), issuerData.getPrivateKey(), password, certificate);
        keyStore.setKeyEntry(subjectData.getX500name().toString(), issuerData.getPrivateKey(), password, certificateChain);
        try (FileOutputStream fos = new FileOutputStream(PATH_LOAD)) {
            keyStore.store(fos, password);
        }
        saveCertificate(csr, certificate.getSerialNumber());
    }

    private void writePrivateKey(String alias, PrivateKey privateKey, char[] password, X509Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IssuerData generateIssuerData(PrivateKey privateKey) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Tim27");
        builder.addRDN(BCStyle.SURNAME, "Adminic");
        builder.addRDN(BCStyle.GIVENNAME, "Admin");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "admin@uns.ac.rs");
        builder.addRDN(BCStyle.UID, "123456");
        return new IssuerData(builder.build(), privateKey);
    }

    @Override
    public SubjectData generateSubjectData(CSR csr) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {

        KeyPair keyPairSubject = generateKeyPair();

        Integer sn = csr.getId();

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, csr.getCommonName());
        builder.addRDN(BCStyle.OU, csr.getOrganizationUnit());
        builder.addRDN(BCStyle.O, csr.getOrganizationName());
        builder.addRDN(BCStyle.L, csr.getLocalityName());
        builder.addRDN(BCStyle.ST, csr.getStateName());
        builder.addRDN(BCStyle.C, csr.getCountry());
        builder.addRDN(BCStyle.E, csr.getEmail());

        return new SubjectData(keyPairSubject.getPublic(), builder.build(),String.valueOf(sn), csr.getStartDate(), csr.getEndDate());
    }

    @Override
    public KeyPair generateKeyPair() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

}

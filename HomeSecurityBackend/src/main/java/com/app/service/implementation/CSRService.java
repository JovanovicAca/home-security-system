package com.app.service.implementation;

import com.app.model.CSR;
import com.app.model.enums.Status;
import com.app.repository.CSRRepository;
import com.app.service.ICSRService;
import com.app.service.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Optional;

@Service
public class CSRService implements ICSRService {

    private final CSRRepository csrRepository;
    private final ICertificateService certificateService;
    private final EmailServiceImpl emailService;
    @Autowired
    public CSRService(CSRRepository csrRepository, ICertificateService certificateService, EmailServiceImpl emailService) {
        this.csrRepository = csrRepository;
        this.certificateService = certificateService;
        this.emailService = emailService;
    }

    @Override
    public List<CSR> findAllCSR() {
        return this.csrRepository.findAll();
    }

    @Override
    public CSR createCSR(CSR csr) {
        emailService.sendMail(csr.getEmail(), "first message","text message");
        boolean validRequest = checkRequest(csr);
        if(!validRequest){
            return null;
        }
        csr.setStatus(Status.PENDING);
        csr.setApproved(false);
        return csrRepository.save(csr);
    }

    @Override
    public CSR updateCSR(CSR csr) {
        boolean validRequest = checkRequest(csr);
        if(!validRequest){
            return null;
        }
        return csrRepository.save(csr);
    }

    @Override
    public CSR acceptCSR(Integer id) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {
        Optional<CSR> optional = csrRepository.findById(id);
        if(optional.isPresent()){
            CSR csr = optional.get();
            csr.setStatus(Status.ACCEPTED);
            certificateService.createCertificate(csr);
            return csrRepository.save(csr);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ne postoji CSR pod ovim ID");
    }

    @Override
    public CSR declineCSR(Integer id) {
        Optional<CSR> optional = csrRepository.findById(id);
        if(optional.isPresent()){
            CSR csr = optional.get();
            csr.setStatus(Status.DECLINED);
            return csrRepository.save(csr);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ne postoji CSR pod ovim ID");
    }

    public boolean checkRequest(CSR csr){

        if (csr.getCommonName() == null || csr.getCommonName().equals("")) {
            return false;
        }
        if (csr.getOrganizationUnit() == null || csr.getOrganizationUnit().equals("")) {
            return false;
        }
        if (csr.getOrganizationName() == null || csr.getOrganizationName().equals("")) {
            return false;
        }
        if (csr.getLocalityName() == null || csr.getLocalityName().equals("")) {
            return false;
        }
        if (csr.getStateName() == null || csr.getStateName().equals("")) {
            return false;
        }
        if (csr.getCountry() == null || csr.getCountry().equals("")) {
            return false;
        }
        if (csr.getEmail() == null || csr.getEmail().equals("")) {
            return false;
        }
//        if(csr.getAlgorithm() == null || csr.getAlgorithm().equals("")){
//            return false;
//        }
//        if(csr.getExtensions().size() <= 0){
//            return false;
//        }
        return true;
    }
}

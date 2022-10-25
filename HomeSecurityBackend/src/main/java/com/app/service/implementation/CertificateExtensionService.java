package com.app.service.implementation;

import com.app.model.CertificateExtension;
import com.app.repository.CertificateExtensionRepository;
import com.app.service.ICertificateExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateExtensionService implements ICertificateExtensionService {

    private final CertificateExtensionRepository certificateExtensionRepository;

    @Autowired
    public CertificateExtensionService(CertificateExtensionRepository certificateExtensionRepository) {
        this.certificateExtensionRepository = certificateExtensionRepository;
    }

    @Override
    public List<CertificateExtension> findAll() {
        return certificateExtensionRepository.findAll();
    }

    @Override
    public void save(CertificateExtension certificateExtension) {
        certificateExtensionRepository.save(certificateExtension);
    }
}

package com.app.service;

import com.app.model.CertificateExtension;

import java.util.List;

public interface ICertificateExtensionService {

    List<CertificateExtension> findAll();
    void save(CertificateExtension certificateExtension);
}

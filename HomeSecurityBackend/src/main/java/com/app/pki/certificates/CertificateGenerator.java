package com.app.pki.certificates;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import com.app.model.CertificateExtension;
import com.app.pki.data.IssuerData;
import com.app.pki.data.SubjectData;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;


public class CertificateGenerator {

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData,
                                               List<CertificateExtension> extensions, PublicKey publicKey) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            for (CertificateExtension extension : extensions) {
                switch (extension.getName()) {
                    case "AKI":
                        certGen = authorityKeyIdentifierExtension(certGen, publicKey);
                        break;
                    case "BC":
                        certGen = basicConstraintsExtension(certGen);
                        break;
                    case "KU":
                        certGen = keyUsageExtension(certGen);
                        break;
                    case "SKI":
                        certGen = subjectKeyIdentifierExtension(certGen, publicKey);
                        break;
                    case "EKU":
                        certGen = extendedKeyUsageExtension(certGen);
                        break;
                    case "SAN":
                        certGen = subjectAlternativeNameExtension(certGen);
                        break;
                }
            }

            X509CertificateHolder certHolder = certGen.build(contentSigner);
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");
            BigInteger num = certConverter.getCertificate(certHolder).getSerialNumber();
            return certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException | NoSuchAlgorithmException | CertIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public X509v3CertificateBuilder authorityKeyIdentifierExtension(X509v3CertificateBuilder certificate,
                                                                    PublicKey publicKey) throws NoSuchAlgorithmException, CertIOException {
        AuthorityKeyIdentifier authorityKeyIdentifier = new JcaX509ExtensionUtils().createAuthorityKeyIdentifier(publicKey);
        certificate.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);
        return certificate;
    }

    public X509v3CertificateBuilder basicConstraintsExtension(X509v3CertificateBuilder certificate) throws CertIOException {
        certificate.addExtension(Extension.basicConstraints, true, new BasicConstraints(0));
        return certificate;
    }

    public X509v3CertificateBuilder keyUsageExtension(X509v3CertificateBuilder certificate) throws CertIOException {
        certificate.addExtension(Extension.keyUsage, true,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment | KeyUsage.dataEncipherment | KeyUsage.keyAgreement | KeyUsage.nonRepudiation));
        return certificate;
    }

    public X509v3CertificateBuilder subjectKeyIdentifierExtension(X509v3CertificateBuilder certificate, PublicKey key) throws CertIOException, NoSuchAlgorithmException {
        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(key);
        certificate.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        return certificate;
    }

    public X509v3CertificateBuilder extendedKeyUsageExtension(X509v3CertificateBuilder certificate) throws CertIOException {
        certificate.addExtension(Extension.extendedKeyUsage, false, new
                ExtendedKeyUsage(new KeyPurposeId[]{KeyPurposeId.id_kp_clientAuth, KeyPurposeId.id_kp_serverAuth}));
        return certificate;
    }

    public X509v3CertificateBuilder subjectAlternativeNameExtension(X509v3CertificateBuilder certificate) {

        return certificate;
    }

}

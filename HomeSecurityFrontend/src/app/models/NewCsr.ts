import { CertificateExtension } from "./CertificateExtension";

export interface NewCsr {
    startDate: Date,
    endDate: Date, 
    commonName: string,
    organizationUnit: string, 
    organizationName: string,
    localityName: string, 
    stateName: string,
    country: string,
    email: string,
    extensions: Array<CertificateExtension>
}
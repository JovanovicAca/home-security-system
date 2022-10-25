export interface Csr {
    id: number,
    startDate: Date,
    endDate: Date, 
    commonName: string,
    organizationUnit: string, 
    organizationName: string,
    localityName: string, 
    stateName: string,
    country: string,
    email: string
}
package com.example.servicenovigrad;

public class Request {
    String email, branchName, serviceName, dateOfBirth, address, hash;
    //Status il false if request is still pending, true if request has been accepted. If request is denied, it's removed from Firebase
    boolean status, allDocuments;

    public Request() {}

    public Request(String email, String branchName, String serviceName) {
        this.email = email;
        this.branchName = branchName;
        this.serviceName = serviceName;
        this.hash = stringHash(email,branchName,serviceName);
    }

    public Request(String email, String branchName, String serviceName, String dateOfBirth, String address, boolean status, boolean allDocuments) {
        this.email = email;
        this.branchName = branchName;
        this.serviceName = serviceName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.status = status;
        this.allDocuments = allDocuments;
        this.hash = stringHash(email,branchName,serviceName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getAllDocuments() {
        return allDocuments;
    }

    public void setAllDocuments(boolean allDocuments) {
        this.allDocuments = allDocuments;
    }

    public String stringHash(String email, String branchName, String serviceName) {
        long h = 0;
        String id = email + branchName + serviceName;
        for (int i = 0; i < id.length(); i++) {
            //5011 is a prime number
            h = h * 5011 + id.charAt(i);
        }
        String hash = Long.toString(h);
        return hash;
    }
}

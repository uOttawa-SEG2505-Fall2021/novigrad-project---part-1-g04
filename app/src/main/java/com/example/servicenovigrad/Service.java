package com.example.servicenovigrad;

public class Service {

    private String serviceName;;
    private boolean proofOfResidence, proofOfStatus, photoID;

    public Service() {}

    public Service(String serviceName, boolean proofOfResidence, boolean proofOfStatus, boolean photoID) {
        this.serviceName = serviceName;
        this.proofOfResidence = proofOfResidence;
        this.proofOfStatus = proofOfStatus;
        this.photoID = photoID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean getProofOfResidence() {
        return proofOfResidence;
    }

    public void setProofOfResidence(boolean proofOfResidence) {
        this.proofOfResidence = proofOfResidence;
    }

    public boolean getProofOfStatus() {
        return proofOfStatus;
    }

    public void setProofOfStatus(boolean proofOfStatus) {
        this.proofOfStatus = proofOfStatus;
    }

    public boolean getPhotoID() {
        return photoID;
    }

    public void setPhotoID(boolean photoID) {
        this.photoID = photoID;
    }
}

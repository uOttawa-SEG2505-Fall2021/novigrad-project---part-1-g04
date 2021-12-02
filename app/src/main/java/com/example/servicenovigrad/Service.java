package com.example.servicenovigrad;

public class Service {

    private String serviceName, userFirstName, userLastName, userDateOfBirth, userAddress;;
    private boolean proofOfResidence, proofOfStatus, photoID;

    public Service() {}

    public Service(String serviceName, boolean proofOfResidence,
                   boolean proofOfStatus, boolean photoID) {
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public void setUserDateOfBirth(String userDateOfBirth) {
        this.userDateOfBirth = userDateOfBirth;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
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

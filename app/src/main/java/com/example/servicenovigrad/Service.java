package com.example.servicenovigrad;

public class Service {

    private String serviceName, userFirstName, userLastName, userDateOfBirth, userAddress;;
    private boolean proofOfResidence, proofOfStatus, photoID;

    public Service(String serviceName, String userFirstName, String userLastName,
                   String userDateOfBirth, String userAddress, boolean proofOfResidence,
                   boolean proofOfStatus, boolean photoID) {
        this.serviceName = serviceName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userDateOfBirth = userDateOfBirth;
        this.userAddress = userAddress;
        this.proofOfResidence = proofOfResidence;
        this.proofOfStatus = proofOfStatus;
        this.photoID = photoID;
    }

    public Service(String serviceName, boolean proofOfResidence,
                   boolean proofOfStatus, boolean photoID) {
        this.serviceName = serviceName;
        this.proofOfResidence = proofOfResidence;
        this.proofOfStatus = proofOfStatus;
        this.photoID = photoID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getUserFirstName() {
        return userLastName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public String getUserLastName() {
        return userFirstName;
    }

    public boolean getProofOfResidence() {
        return proofOfResidence;
    }

    public boolean getProofOfStatus() {
        return proofOfStatus;
    }

    public boolean getPhotoID() {
        return photoID;
    }


    public void setProofOfResidence(boolean proofOfResidence) {
        this.proofOfResidence = proofOfResidence;
    }

    public void setProofOfStatus(boolean proofOfStatus) {
        this.proofOfStatus = proofOfStatus;
    }

    public void setPhotoID(boolean photoID) {
        this.photoID = photoID;
    }
}

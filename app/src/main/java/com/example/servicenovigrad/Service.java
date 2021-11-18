package com.example.servicenovigrad;

public class Service {

    private String serviceName;
    private String userFirstName;
    private String userLastName;
    private String userDateOfBirth;
    private String userAddress;
    private boolean proofOfResidence;
    private boolean proofOfStatus;
    private boolean photoID;

    public Service() {}

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}

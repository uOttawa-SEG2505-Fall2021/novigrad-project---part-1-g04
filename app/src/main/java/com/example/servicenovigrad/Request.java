package com.example.servicenovigrad;

public class Request {
    String email, branchName, serviceName;

    public Request() {}

    public Request(String email, String branchName, String serviceName) {
        this.email = email;
        this.branchName = branchName;
        this.serviceName = serviceName;
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
}

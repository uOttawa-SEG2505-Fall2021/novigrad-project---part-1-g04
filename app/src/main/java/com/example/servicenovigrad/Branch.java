package com.example.servicenovigrad;

import java.util.ArrayList;

public class Branch {

    private String branchName, address, phoneNumber;
    private int startHour, startMinute, endHour, endMinute;
    private ArrayList<String> services, openDays;

    public Branch() {}

    public Branch(String branchName, String address, String phoneNumber, int startHour, int startMinute, int endHour, int endMinute,
                  ArrayList<String> services, ArrayList<String> openDays) {
        this.branchName = branchName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.services = services;
        this.openDays = openDays;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public ArrayList<String> getOpenDays() {
        return openDays;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public void setOpenDays(ArrayList<String> openDays) {
        this.openDays = openDays;
    }
}
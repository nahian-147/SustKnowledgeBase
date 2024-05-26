package com.example.sustknowledgebase.models;

public class StudentInfo {
    public String fullName;
    public String department;
    public Integer registration;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    public StudentInfo(String fullName, String department, Integer registration) {
        this.fullName = fullName;
        this.department = department;
        this.registration = registration;
    }
}

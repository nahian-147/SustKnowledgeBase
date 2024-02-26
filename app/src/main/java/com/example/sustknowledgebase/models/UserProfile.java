package com.example.sustknowledgebase.models;

import java.math.BigInteger;
import java.util.ArrayList;

public class UserProfile {

    public class Team{
        BigInteger id;
        String name;
        Integer year;
        String projectThesisTitle;
        String course;

        public BigInteger getId() {
            return id;
        }

        public void setId(BigInteger id) {
            this.id = id;
        }
    }

    public class UserData{
        BigInteger id;
        String fullName;
        String email;
        String expertise;
        ArrayList<String> publications;
        String department;

        public BigInteger getId() {
            return id;
        }

        public void setId(BigInteger id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getExpertise() {
            return expertise;
        }

        public void setExpertise(String expertise) {
            this.expertise = expertise;
        }

        public ArrayList<String> getPublications() {
            return publications;
        }

        public void setPublications(ArrayList<String> publications) {
            this.publications = publications;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }

    private UserData userData;
    private ArrayList<Team> teams;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}

package com.rajesh.interviewjudge.dto;

public class CandidateDTO {

    private String name;
    private String email;
    private String phone;

    private int technicalScore;
    private int communicationScore;
    private int aptitudeScore;

    private double finalScore;
    private String result;

    public CandidateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(int technicalScore) {
        this.technicalScore = technicalScore;
    }

    public int getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(int communicationScore) {
        this.communicationScore = communicationScore;
    }

    public int getAptitudeScore() {
        return aptitudeScore;
    }

    public void setAptitudeScore(int aptitudeScore) {
        this.aptitudeScore = aptitudeScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
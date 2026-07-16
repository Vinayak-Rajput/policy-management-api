package com.vinayak.policyapi.ppolicy_api.models;

public class Proposal {
    
    private int proposalId;
    private int customerId;
    private int policyId;
    private int policyTerm;
    private double sumAssured;
    private double annualPremium;
    private String status;
    private String policyNumber;

    public Proposal() {
    }

    public int getProposalId() {
        return proposalId;
    }

    public void setProposalId(int proposalId) {
        this.proposalId = proposalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public int getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(int policyTerm) {
        this.policyTerm = policyTerm;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public double getAnnualPremium() {
        return annualPremium;
    }

    public void setAnnualPremium(double annualPremium) {
        this.annualPremium = annualPremium;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

}

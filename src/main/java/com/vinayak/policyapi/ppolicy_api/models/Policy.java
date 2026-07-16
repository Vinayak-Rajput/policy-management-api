package com.vinayak.policyapi.ppolicy_api.models;

public class Policy {
    
    private int policyId;
    private String policyName;
    private String policyType;
    private double sumAssured;
    private double maxSumAssured;

    public Policy() {
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public double getMaxSumAssured() {
        return maxSumAssured;
    }

    public void setMaxSumAssured(double maxSumAssured) {
        this.maxSumAssured = maxSumAssured;
    }

}

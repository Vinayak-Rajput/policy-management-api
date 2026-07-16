package com.vinayak.policyapi.ppolicy_api.dataTransferObjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

public class ProposalRequestDTO {

    @NotNull(message = "CustomerID = mandatory")
    private int customerId;

    @NotNull(message = "OfferedPolicyID = mandatory")
    private int offeredPolicyId;

    @NotNull(message = "PolicyTerm = mandatory")
    private int policyTerm;

    @NotNull(message = "SumAssured = mandatory")
    @DecimalMin(value = "100000.00", message = "Sum Assured >= 1_00_000")
    @DecimalMax(value = "50000000.00", message = "Sum Assured < 5_00_00_000")
    private double sumAssured;

    @NotNull(message = "AnnualPremium = mandatory")
    @DecimalMin(value = "5000.00", message = "Annual Premium must be at least Rs. 5,000")
    private double annualPremium;

    @NotBlank(message = "Paymentfrequency = mandatory")
    private String paymentFrequency;

    public int getCustomerId() { 
        return customerId; 
    }

    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    
    }

    public int getOfferedPolicyId() { 
        return offeredPolicyId; 
    }

    public void setOfferedPolicyId(int offeredPolicyId) { 
        this.offeredPolicyId = offeredPolicyId; 
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

    public String getPaymentFrequency() { 
        return paymentFrequency; 
    }

    public void setPaymentFrequency(String paymentFrequency) { 
        this.paymentFrequency = paymentFrequency; 
    }

}

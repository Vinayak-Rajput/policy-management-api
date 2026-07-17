package com.vinayak.policyapi.ppolicy_api.services;

import com.vinayak.policyapi.ppolicy_api.models.Policy;
import com.vinayak.policyapi.ppolicy_api.repository.PolicyRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepo policyRepository;

    public PolicyService(PolicyRepo policyRepository) {
        this.policyRepository = policyRepository;
    }

    @PostConstruct
    public void loadDefaultPolicies() {
        Policy termLife = new Policy();
        termLife.setPolicyName("Secure Term Life");
        termLife.setPolicyType("TERM");
        termLife.setSumAssured(100000.00);   
        termLife.setMaxSumAssured(50000000.00); 
        policyRepository.save(termLife);

        Policy health = new Policy();
        health.setPolicyName("Family Health Plus");
        health.setPolicyType("HEALTH");
        health.setSumAssured(500000.00);     
        health.setMaxSumAssured(2500000.00);
        policyRepository.save(health);
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(int id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with ID: " + id));
    }
}
package com.vinayak.policyapi.ppolicy_api.controller;

import com.vinayak.policyapi.ppolicy_api.models.Policy;
import com.vinayak.policyapi.ppolicy_api.services.PolicyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable int id) {
        Policy policy = policyService.getPolicyById(id);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }
}  

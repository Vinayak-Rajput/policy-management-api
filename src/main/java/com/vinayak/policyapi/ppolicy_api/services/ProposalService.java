package com.vinayak.policyapi.ppolicy_api.services;

import com.vinayak.policyapi.ppolicy_api.models.Customer;
import com.vinayak.policyapi.ppolicy_api.models.Policy;
import com.vinayak.policyapi.ppolicy_api.models.Proposal;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.ProposalRequestDTO;
import com.vinayak.policyapi.ppolicy_api.repository.CustomerRepo;
import com.vinayak.policyapi.ppolicy_api.repository.PolicyRepo;
import com.vinayak.policyapi.ppolicy_api.repository.ProposalRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProposalService {

    private final ProposalRepo proposalRepository;
    private final CustomerRepo customerRepository;
    private final PolicyRepo policyRepository;
    private final AuditService auditService; 

    public ProposalService(ProposalRepo proposalRepository, 
                           CustomerRepo customerRepository, 
                           PolicyRepo policyRepository,
                           AuditService auditService) {
        this.proposalRepository = proposalRepository;
        this.customerRepository = customerRepository;
        this.policyRepository = policyRepository;
        this.auditService = auditService;
    }

    public Proposal createProposal(ProposalRequestDTO dto){
        Customer customer = customerRepository.findById(dto.getCustomerId())
                            .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + dto.getCustomerId()));
        Policy policy = policyRepository.findById(dto.getOfferedPolicyId())
                            .orElseThrow(() -> new IllegalArgumentException("Policy not found with ID: " + dto.getOfferedPolicyId()));
        
        if (dto.getSumAssured() < policy.getSumAssured() || dto.getSumAssured() > policy.getMaxSumAssured()) {
            throw new IllegalArgumentException("Business Rule Violation: Sum Assured is outside limits");
        }

        if (dto.getAnnualPremium() > 50000 && customer.getPanNumber() == null){
            throw new IllegalArgumentException("Business Rule Violation: PAN number is mandated; annual premium > 50,000");
        }

        List<Integer> policyTerms = new ArrayList<>(List.of(10,15,20,25,30));
        if(!policyTerms.contains(dto.getPolicyTerm())){
            throw new IllegalArgumentException("Business Rule Violation: Invalid policy term.");
        }

        Proposal proposal = new Proposal();
        proposal.setCustomerId(dto.getCustomerId());
        proposal.setPolicyId(dto.getOfferedPolicyId()); 
        proposal.setPolicyTerm(dto.getPolicyTerm());
        proposal.setSumAssured(dto.getSumAssured());
        proposal.setAnnualPremium(dto.getAnnualPremium());
        
        proposal.setStatus("DRAFT");
        
        return proposalRepository.save(proposal);

    }

    public Proposal submitProposal(ProposalRequestDTO dto) {

        customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID."));

        Policy policy = policyRepository.findById(dto.getOfferedPolicyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Policy ID."));

        if (dto.getSumAssured() < policy.getSumAssured() || dto.getSumAssured() > policy.getMaxSumAssured()) {
            throw new IllegalArgumentException("Business Rule Violation: Sum Assured is outside limits");
        }

        Proposal proposal = new Proposal();
        proposal.setCustomerId(dto.getCustomerId());
        proposal.setPolicyId(dto.getOfferedPolicyId()); 
        proposal.setPolicyTerm(dto.getPolicyTerm());
        proposal.setSumAssured(dto.getSumAssured());
        proposal.setAnnualPremium(dto.getAnnualPremium());
        
        proposal.setStatus("SUBMITTED");
        proposal.setPolicyNumber("POL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        auditService.logAction("Proposal submitted for Customer ID: " + dto.getCustomerId() + ", Policy ID: " + dto.getOfferedPolicyId());
        return proposalRepository.save(proposal);
    }

    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(int id) {
        return proposalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proposal not found with ID: " + id));
    }
}
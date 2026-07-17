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

        if (dto.getAnnualPremium() > 50000 && (customer.getPanNumber() == null || customer.getPanNumber().trim().isEmpty())){
            throw new IllegalArgumentException("PAN is mandatory because the Annual Premium exceeds Rs. 50,000.");
        }

        List<Integer> policyTerms = new ArrayList<>(List.of(10,15,20,25,30));
        
        if(!policyTerms.contains(dto.getPolicyTerm())){
            throw new IllegalArgumentException("Policy Term must be 10, 15, 20, 25, or 30 years.");
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

    public Proposal submitProposal(int proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new IllegalArgumentException("Proposal not found with ID: " + proposalId));

        if (!proposal.getStatus().equals("DRAFT")) {
            throw new IllegalArgumentException("Only DRAFT proposals can be submitted.");
        }

        proposal.setStatus("SUBMITTED");

        proposal.setPolicyNumber("POL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        Proposal savedProposal = proposalRepository.save(proposal);

        auditService.logAction("Proposal Submitted: " + savedProposal.getPolicyNumber() + " for Customer ID: " + savedProposal.getCustomerId());
        return savedProposal;
    }

    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(int id) {
        return proposalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proposal not found with ID: " + id));
    }
}
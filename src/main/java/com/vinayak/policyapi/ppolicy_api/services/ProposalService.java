package com.vinayak.policyapi.ppolicy_api.services;

import com.vinayak.policyapi.ppolicy_api.models.Policy;
import com.vinayak.policyapi.ppolicy_api.models.Proposal;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.ProposalRequestDTO;
import com.vinayak.policyapi.ppolicy_api.repository.CustomerRepo;
import com.vinayak.policyapi.ppolicy_api.repository.PolicyRepo;
import com.vinayak.policyapi.ppolicy_api.repository.ProposalRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProposalService {

    private final ProposalRepo proposalRepository;
    private final CustomerRepo customerRepository;
    private final PolicyRepo policyRepository;

    public ProposalService(ProposalRepo proposalRepository, 
                           CustomerRepo customerRepository, 
                           PolicyRepo policyRepository) {
        this.proposalRepository = proposalRepository;
        this.customerRepository = customerRepository;
        this.policyRepository = policyRepository;
    }

    public Proposal submitProposal(ProposalRequestDTO dto) {

        customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID: Customer does not exist."));

        Policy policy = policyRepository.findById(dto.getOfferedPolicyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Policy ID: Policy does not exist."));

        if (dto.getSumAssured() < policy.getSumAssured() || dto.getSumAssured() > policy.getMaxSumAssured()) {
            throw new IllegalArgumentException("Business Rule Violation: Requested Sum Assured is outside the allowed limits for this policy.");
        }

        Proposal proposal = new Proposal();
        proposal.setCustomerId(dto.getCustomerId());
        proposal.setPolicyId(dto.getOfferedPolicyId()); 
        proposal.setPolicyTerm(dto.getPolicyTerm());
        proposal.setSumAssured(dto.getSumAssured());
        proposal.setAnnualPremium(dto.getAnnualPremium());
        
        proposal.setStatus("SUBMITTED");
        proposal.setPolicyNumber("POL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

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
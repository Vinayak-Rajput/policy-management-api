package com.vinayak.policyapi.ppolicy_api;

import com.vinayak.policyapi.ppolicy_api.models.Customer;
import com.vinayak.policyapi.ppolicy_api.models.Policy;
import com.vinayak.policyapi.ppolicy_api.models.Proposal;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.ProposalRequestDTO;
import com.vinayak.policyapi.ppolicy_api.repository.CustomerRepo;
import com.vinayak.policyapi.ppolicy_api.repository.PolicyRepo;
import com.vinayak.policyapi.ppolicy_api.repository.ProposalRepo;
import com.vinayak.policyapi.ppolicy_api.services.AuditService;
import com.vinayak.policyapi.ppolicy_api.services.ProposalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProposalServiceTest {

    @Mock
    private ProposalRepo proposalRepository;
    @Mock
    private CustomerRepo customerRepository;
    @Mock
    private PolicyRepo policyRepository;
    @Mock
    private AuditService auditService;

    @InjectMocks
    private ProposalService proposalService;

    private Customer validCustomer;
    private Policy validPolicy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validCustomer = new Customer();
        validCustomer.setCustomerId(1);
        validCustomer.setPanNumber("ABCDE1234F");

        validPolicy = new Policy();
        validPolicy.setPolicyId(1);
        validPolicy.setSumAssured(100000);
        validPolicy.setMaxSumAssured(1000000);
    }

    @Test
    void testCreateProposal_Success() {
        ProposalRequestDTO dto = new ProposalRequestDTO();
        dto.setCustomerId(1);
        dto.setOfferedPolicyId(1);
        dto.setPolicyTerm(10); 
        dto.setSumAssured(500000);
        dto.setAnnualPremium(12000);

        when(customerRepository.findById(1)).thenReturn(Optional.of(validCustomer));
        when(policyRepository.findById(1)).thenReturn(Optional.of(validPolicy));
        
        Proposal mockSavedProposal = new Proposal();
        mockSavedProposal.setStatus("DRAFT");
        when(proposalRepository.save(any(Proposal.class))).thenReturn(mockSavedProposal);

        Proposal result = proposalService.createProposal(dto);

        assertNotNull(result);
        assertEquals("DRAFT", result.getStatus());
    }

    @Test
    void testCreateProposal_FailsOnInvalidPolicyTerm() {
        ProposalRequestDTO dto = new ProposalRequestDTO();
        dto.setCustomerId(1);
        dto.setOfferedPolicyId(1);
        dto.setPolicyTerm(12); 
        dto.setSumAssured(500000);

        when(customerRepository.findById(1)).thenReturn(Optional.of(validCustomer));
        when(policyRepository.findById(1)).thenReturn(Optional.of(validPolicy));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            proposalService.createProposal(dto);
        });

        assertEquals("Policy Term must be 10, 15, 20, 25, or 30 years.", exception.getMessage());
    }

    @Test
    void testCreateProposal_FailsWhenHighPremiumLacksPan() {

        validCustomer.setPanNumber(""); 

        ProposalRequestDTO dto = new ProposalRequestDTO();
        dto.setCustomerId(1);
        dto.setOfferedPolicyId(1);
        dto.setPolicyTerm(10);
        dto.setSumAssured(500000);
        dto.setAnnualPremium(60000); // > 50,000 threshold

        when(customerRepository.findById(1)).thenReturn(Optional.of(validCustomer));
        when(policyRepository.findById(1)).thenReturn(Optional.of(validPolicy));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            proposalService.createProposal(dto);
        });

        assertEquals("PAN is mandatory because the Annual Premium exceeds Rs. 50,000.", exception.getMessage());
    }
}
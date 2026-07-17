package com.vinayak.policyapi.ppolicy_api.controller;

import com.vinayak.policyapi.ppolicy_api.models.Proposal;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.ProposalRequestDTO;
import com.vinayak.policyapi.ppolicy_api.services.ProposalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping
    public ResponseEntity<Proposal> createProposal(@Valid @RequestBody ProposalRequestDTO requestDTO) {
        Proposal createdProposal = proposalService.createProposal(requestDTO);
        return new ResponseEntity<>(createdProposal, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Proposal> submitProposal(@PathVariable int id) {
        Proposal submittedProposal = proposalService.submitProposal(id);
        return new ResponseEntity<>(submittedProposal, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Proposal>> getAllProposals() {
        List<Proposal> proposals = proposalService.getAllProposals();
        return new ResponseEntity<>(proposals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposalById(@PathVariable int id) {
        Proposal proposal = proposalService.getProposalById(id);
        return new ResponseEntity<>(proposal, HttpStatus.OK);
    }
}
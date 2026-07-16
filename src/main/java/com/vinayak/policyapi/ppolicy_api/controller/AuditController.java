package com.vinayak.policyapi.ppolicy_api.controller;

import com.vinayak.policyapi.ppolicy_api.models.Audit;
import com.vinayak.policyapi.ppolicy_api.services.AuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audits")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<Audit>> getAllAudits() {
        return new ResponseEntity<>(auditService.getAllAudits(), HttpStatus.OK);
    }
}
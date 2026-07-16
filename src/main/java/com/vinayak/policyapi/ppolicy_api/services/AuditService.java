package com.vinayak.policyapi.ppolicy_api.services;

import com.vinayak.policyapi.ppolicy_api.models.Audit;
import com.vinayak.policyapi.ppolicy_api.repository.AuditRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    private final AuditRepo auditRepository;

    public AuditService(AuditRepo auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void logAction(String actionDescription) {
        Audit audit = new Audit();
        audit.setActionDescription(actionDescription);
        audit.setActionTimestamp(LocalDateTime.now());
        auditRepository.save(audit);
    }

    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }
}
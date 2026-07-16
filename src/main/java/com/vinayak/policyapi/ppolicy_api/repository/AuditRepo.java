package com.vinayak.policyapi.ppolicy_api.repository;

import com.vinayak.policyapi.ppolicy_api.models.Audit;
import org.springframework.stereotype.Repository;

@Repository
public class AuditRepo extends Repo<Audit> {

    @Override
    protected void setId(Audit entity, int id) {
        entity.setAuditId(id);
    }

    @Override
    protected int getId(Audit entity) {
        return entity.getAuditId();
    }
}
package com.vinayak.policyapi.ppolicy_api.repository;

import com.vinayak.policyapi.ppolicy_api.models.Policy;
import org.springframework.stereotype.Repository;

@Repository
public class PolicyRepo extends Repo<Policy> {

    @Override
    protected void setId(Policy entity, int id) {
        entity.setPolicyId(id);
    }

    @Override
    protected int getId(Policy entity) {
        return entity.getPolicyId();
    }
}
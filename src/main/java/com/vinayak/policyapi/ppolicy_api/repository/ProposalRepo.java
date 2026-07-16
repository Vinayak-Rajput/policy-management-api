package com.vinayak.policyapi.ppolicy_api.repository;

import com.vinayak.policyapi.ppolicy_api.models.Proposal;
import org.springframework.stereotype.Repository;

@Repository
public class ProposalRepo extends Repo<Proposal> {

    @Override
    protected void setId(Proposal entity, int id) {
        entity.setProposalId(id);
    }

    @Override
    protected int getId(Proposal entity) {
        return entity.getProposalId();
    }
}
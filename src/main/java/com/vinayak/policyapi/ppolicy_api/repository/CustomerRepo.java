package com.vinayak.policyapi.ppolicy_api.repository;
import com.vinayak.policyapi.ppolicy_api.models.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepo extends Repo<Customer> {
    @Override
    protected void setId(Customer entity, int id) {
        entity.setCustomerId(id);
    }

    @Override
    protected int getId(Customer entity) {
        return entity.getCustomerId();
    }
}
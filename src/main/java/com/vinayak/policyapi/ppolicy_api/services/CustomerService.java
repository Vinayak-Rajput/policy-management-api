package com.vinayak.policyapi.ppolicy_api.services;

import com.vinayak.policyapi.ppolicy_api.models.Customer;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.CustomerRequestDTO;
import com.vinayak.policyapi.ppolicy_api.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepository;

    // Spring Boot automatically injects the repository here
    public CustomerService(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerRequestDTO dto) {
        // Business Rule: Nominee cannot be the customer themselves
        if (dto.getName().trim().equalsIgnoreCase(dto.getNomineeName().trim())) {
            throw new IllegalArgumentException("Business Rule Violation: A customer cannot be their own nominee.");
        }

        // Convert the validated DTO into our internal Domain Model
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName(dto.getName());
        newCustomer.setAge(dto.getAge());
        newCustomer.setPanNumber(dto.getPanNumber());
        newCustomer.setNomineeName(dto.getNomineeName());

        // Save it to our thread-safe repository
        return customerRepository.save(newCustomer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
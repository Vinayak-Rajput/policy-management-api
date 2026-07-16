package com.vinayak.policyapi.ppolicy_api.services;

import com.vinayak.policyapi.ppolicy_api.models.Customer;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.CustomerRequestDTO;
import com.vinayak.policyapi.ppolicy_api.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepository;

    public CustomerService(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerRequestDTO dto) {

        if (dto.getName().trim().equalsIgnoreCase(dto.getNomineeName().trim())) {
            throw new IllegalArgumentException("Business Rule Violation: A customer cannot be their own nominee.");
        }

        Customer newCustomer = new Customer();
        newCustomer.setCustomerName(dto.getName());
        newCustomer.setAge(dto.getAge());
        newCustomer.setPanNumber(dto.getPanNumber());
        newCustomer.setNomineeName(dto.getNomineeName());

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
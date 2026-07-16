package com.vinayak.policyapi.ppolicy_api.controller;

import com.vinayak.policyapi.ppolicy_api.models.Customer;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.CustomerRequestDTO;
import com.vinayak.policyapi.ppolicy_api.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 1. POST Endpoint to create a customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequestDTO requestDTO) {
        Customer createdCustomer = customerService.createCustomer(requestDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // 2. GET Endpoint to fetch all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // 3. GET Endpoint to fetch a specific customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}